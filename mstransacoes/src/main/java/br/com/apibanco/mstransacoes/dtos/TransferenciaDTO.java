package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TransferenciaDTO(
        @NotNull(message = "O ID do cliente é obrigatório") Long idCliente,
        @NotBlank(message = "O número da conta de origem é obrigatório") String numeroContaOrigem,
        @NotBlank(message = "O número da conta de destino é obrigatório") String numeroContaDestino,
        @NotNull(message = "O valor é obrigatório") @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero") BigDecimal valor) {

}
