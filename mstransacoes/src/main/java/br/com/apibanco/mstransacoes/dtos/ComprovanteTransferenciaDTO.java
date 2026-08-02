package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ComprovanteTransferenciaDTO(
                String numeroContaOrigem,
                String numeroContaDestino,
                BigDecimal valor,
                BigDecimal saldo,
                LocalDateTime dataTransferencia) {

}
