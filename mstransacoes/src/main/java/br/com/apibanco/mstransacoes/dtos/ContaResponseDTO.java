package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;

import br.com.apibanco.mstransacoes.entities.Cliente;
import br.com.apibanco.mstransacoes.entities.Conta;

public record ContaResponseDTO(
        Long id,
        Cliente cliente,
        String numero,
        String tipo,
        BigDecimal saldo) {
    // Construtor compacto para mapear facilmente da entidade para o DTO
    public ContaResponseDTO(Conta conta) {
        this(conta.getId(), conta.getCliente(), conta.getNumero(), conta.getTipo().name(), conta.getSaldo());
    }
}
