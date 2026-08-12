package br.com.apibanco.msextratoconsumer.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ExtratoResponseDTO(
        UUID transactionId,
        String type,
        BigDecimal amount,
        String accountDestination,
        String status,
        Instant transactionTime) {
}
