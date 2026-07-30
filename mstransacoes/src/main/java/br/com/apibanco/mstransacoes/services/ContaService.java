package br.com.apibanco.mstransacoes.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.apibanco.mstransacoes.dtos.ContaMovimentacaoDTO;
import br.com.apibanco.mstransacoes.dtos.ContaTransferenciaDTO;
import br.com.apibanco.mstransacoes.entities.Conta;
import br.com.apibanco.mstransacoes.exceptions.ContaNotFoundException;
import br.com.apibanco.mstransacoes.repositories.ContaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {
    private final ContaRepository contaRepository;

    @Transactional
    public Conta depositar(ContaMovimentacaoDTO movimentacaoDTO) {
        var conta = contaRepository.findByNumero(movimentacaoDTO.numeroConta())
                .orElseThrow(
                        () -> new ContaNotFoundException("Conta não encontrada: " + movimentacaoDTO.numeroConta()));

        atualizarSaldo(conta.getId(), movimentacaoDTO.valor(), "DEPOSITO");

        return conta;
    }

    @Transactional
    public Conta sacar(ContaMovimentacaoDTO movimentacaoDTO) {
        var conta = contaRepository.findByNumeroAndIdCliente(movimentacaoDTO.numeroConta(), movimentacaoDTO.idCliente())
                .orElseThrow(
                        () -> new ContaNotFoundException("Conta não encontrada: " + movimentacaoDTO.numeroConta()));

        atualizarSaldo(conta.getId(), movimentacaoDTO.valor(), "SAQUE");
        return conta;
    }

    @Transactional
    public void transferir(ContaTransferenciaDTO contaTransferenciaDTO) {
        if (contaTransferenciaDTO.numeroContaOrigem().equals(contaTransferenciaDTO.numeroContaDestino())) {
            throw new IllegalArgumentException("A conta de origem e destino não podem ser iguais.");
        }

        var contaOrigem = contaRepository
                .findByNumeroAndIdCliente(contaTransferenciaDTO.numeroContaOrigem(), contaTransferenciaDTO.idCliente())
                .orElseThrow(() -> new ContaNotFoundException(
                        "Conta não encontrada: " + contaTransferenciaDTO.numeroContaOrigem()));

        var contaDestino = contaRepository.findByNumero(contaTransferenciaDTO.numeroContaDestino())
                .orElseThrow(() -> new ContaNotFoundException(
                        "Conta não encontrada: " + contaTransferenciaDTO.numeroContaDestino()));

        // Realiza as operações utilizando as regras internas da entidade
        atualizarSaldo(contaOrigem.getId(), contaTransferenciaDTO.valor(), "SAQUE");
        atualizarSaldo(contaDestino.getId(), contaTransferenciaDTO.valor(), "DEPOSITO");

        // contaRepository.save(contaOrigem);
        // contaRepository.save(contaDestino);
    }

    @Transactional
    private void atualizarSaldo(Long idConta, BigDecimal valor, String operacao) {
        var conta = contaRepository.findById(idConta)
                .orElseThrow(() -> new ContaNotFoundException("Conta não encontrada"));

        BigDecimal novoSaldo;

        if (operacao.equals("SAQUE")) {
            if (conta.getSaldo().compareTo(valor) < 0) {
                throw new RuntimeException("Saldo insuficiente para saque");
            }
            novoSaldo = conta.getSaldo().subtract(valor);
        } else { // Depósito
            novoSaldo = conta.getSaldo().add(valor);
        }
        conta.setSaldo(novoSaldo);

        contaRepository.save(conta);
    }
}
