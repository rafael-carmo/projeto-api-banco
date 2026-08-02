package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepositoDTO(
                @NotBlank(message = "O número da conta é obrigatório") String numeroConta,
                @NotNull(message = "O valor é obrigatório") @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero") BigDecimal valor) {

}
