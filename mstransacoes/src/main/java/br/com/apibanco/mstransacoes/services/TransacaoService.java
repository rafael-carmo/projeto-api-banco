package br.com.apibanco.mstransacoes.services;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apibanco.mstransacoes.dtos.DepositoDTO;
import br.com.apibanco.mstransacoes.dtos.SaqueDTO;
import br.com.apibanco.mstransacoes.dtos.TransactionEventDTO;
import br.com.apibanco.mstransacoes.dtos.TransferenciaDTO;
import br.com.apibanco.mstransacoes.dtos.ComprovanteDepositoDTO;
import br.com.apibanco.mstransacoes.dtos.ComprovanteSaqueDTO;
import br.com.apibanco.mstransacoes.dtos.ComprovanteTransferenciaDTO;
import br.com.apibanco.mstransacoes.entities.Conta;
import br.com.apibanco.mstransacoes.exceptions.ContaNotFoundException;
import br.com.apibanco.mstransacoes.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final ContaRepository contaRepository;
    private final TransactionEventProducer transactionEventProducer;

    @Transactional
    public ComprovanteDepositoDTO depositar(DepositoDTO movimentacaoDTO) {
        var conta = contaRepository.findByNumero(movimentacaoDTO.numeroConta())
                .orElseThrow(
                        () -> new ContaNotFoundException("Conta não encontrada: " + movimentacaoDTO.numeroConta()));

        atualizarSaldo(conta, movimentacaoDTO.valor(), "DEPOSITO");

        // inserir informação no broker de mensagens Kafka, para notificar outros
        // serviços sobre a movimentação da conta
        UUID transactionId = UUID.randomUUID();

        var transactionEvent = new TransactionEventDTO(
                transactionId,
                null, // Conta Origem (nulo em depósitos)
                conta.getNumero(), // Conta Destino
                movimentacaoDTO.valor(),
                "DEPOSIT",
                "COMPLETED",
                Instant.now());

        transactionEventProducer.sendTransactionEvent(transactionEvent);

        return new ComprovanteDepositoDTO(conta, movimentacaoDTO.valor(),
                Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Transactional
    public ComprovanteSaqueDTO sacar(SaqueDTO movimentacaoDTO) {
        var conta = contaRepository.findByNumeroAndClienteId(movimentacaoDTO.numeroConta(), movimentacaoDTO.idCliente())
                .orElseThrow(
                        () -> new ContaNotFoundException("Conta não encontrada: " + movimentacaoDTO.numeroConta()));

        atualizarSaldo(conta, movimentacaoDTO.valor(), "SAQUE");

        // inserir informação no broker de mensagens Kafka, para notificar outros
        // serviços sobre a movimentação da conta
        // Cria e envia o evento de Saque para o Kafka
        UUID transactionId = UUID.randomUUID();

        var transactionEvent = new TransactionEventDTO(
                transactionId,
                conta.getNumero(), // Conta Origem (quem está sacando)
                null, // Conta Destino (nulo em saques)
                movimentacaoDTO.valor(),
                "WITHDRAW",
                "COMPLETED",
                Instant.now());

        transactionEventProducer.sendTransactionEvent(transactionEvent);

        return new ComprovanteSaqueDTO(conta, movimentacaoDTO.valor(),
                Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Transactional
    public ComprovanteTransferenciaDTO transferir(TransferenciaDTO transferenciaDTO) {
        if (transferenciaDTO.numeroContaOrigem().equals(transferenciaDTO.numeroContaDestino())) {
            throw new IllegalArgumentException("A conta de origem e destino não podem ser iguais.");
        }

        var contaOrigem = contaRepository
                .findByNumeroAndClienteId(transferenciaDTO.numeroContaOrigem(), transferenciaDTO.idCliente())
                .orElseThrow(() -> new ContaNotFoundException(
                        "Conta não encontrada: " + transferenciaDTO.numeroContaOrigem()));

        var contaDestino = contaRepository.findByNumero(transferenciaDTO.numeroContaDestino())
                .orElseThrow(() -> new ContaNotFoundException(
                        "Conta não encontrada: " + transferenciaDTO.numeroContaDestino()));

        // Realiza as operações utilizando as regras internas da entidade
        atualizarSaldo(contaOrigem, transferenciaDTO.valor(), "SAQUE");
        atualizarSaldo(contaDestino, transferenciaDTO.valor(), "DEPOSITO");

        // contaRepository.save(contaOrigem);
        // contaRepository.save(contaDestino);

        // inserir informação no broker de mensagens Kafka, para notificar outros
        // serviços sobre a movimentação da conta

        // Cria e envia o evento de Transferência completa para o Kafka
        UUID transactionId = UUID.randomUUID();

        var transactionEvent = new TransactionEventDTO(
                transactionId,
                contaOrigem.getNumero(),
                contaDestino.getNumero(),
                transferenciaDTO.valor(),
                "TRANSFER",
                "COMPLETED",
                Instant.now());

        transactionEventProducer.sendTransactionEvent(transactionEvent);

        return new ComprovanteTransferenciaDTO(
                contaOrigem.getNumero(),
                contaDestino.getNumero(),
                transferenciaDTO.valor(),
                contaOrigem.getSaldo(),
                Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime());
    }

    @Transactional
    private void atualizarSaldo(Conta conta, BigDecimal valor, String operacao) {

        BigDecimal novoSaldo;

        if (operacao.equals("SAQUE")) {
            if (conta.getSaldo().compareTo(valor) < 0) {
                throw new RuntimeException("Saldo insuficiente para saque/transferência.");
            }
            novoSaldo = conta.getSaldo().subtract(valor);
        } else { // Depósito
            novoSaldo = conta.getSaldo().add(valor);
        }
        conta.setSaldo(novoSaldo);

        contaRepository.save(conta);
    }
}
