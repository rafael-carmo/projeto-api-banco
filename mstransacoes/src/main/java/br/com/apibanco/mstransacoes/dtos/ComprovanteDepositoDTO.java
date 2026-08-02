package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.apibanco.mstransacoes.entities.Conta;

public record ComprovanteDepositoDTO(
        String numero,
        String tipo,
        BigDecimal valorDeposito,
        LocalDateTime dataDeposito) {
    // Construtor compacto para mapear facilmente da entidade para o DTO
    public ComprovanteDepositoDTO(Conta conta, BigDecimal valorDeposito, LocalDateTime dataDeposito) {
        this(conta.getNumero(), conta.getTipo().name(), valorDeposito,
                dataDeposito);
    }
}
