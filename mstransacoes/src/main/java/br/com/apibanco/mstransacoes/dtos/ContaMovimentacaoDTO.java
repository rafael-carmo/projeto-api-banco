package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ContaMovimentacaoDTO(
                Long idCliente,
                @NotBlank String numeroConta,
                @NotNull @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero") BigDecimal valor) {

}
