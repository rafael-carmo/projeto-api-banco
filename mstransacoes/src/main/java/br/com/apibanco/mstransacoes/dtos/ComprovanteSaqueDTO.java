package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.apibanco.mstransacoes.entities.Conta;

public record ComprovanteSaqueDTO(
        Long id,
        String numero,
        String tipo,
        BigDecimal saldo,
        BigDecimal valorSaque,
        LocalDateTime dataSaque) {
    // Construtor compacto para mapear facilmente da entidade para o DTO
    public ComprovanteSaqueDTO(Conta conta, BigDecimal valorSaque, LocalDateTime dataSaque) {
        this(conta.getId(), conta.getNumero(), conta.getTipo().name(), conta.getSaldo(), valorSaque,
                dataSaque);
    }
}
