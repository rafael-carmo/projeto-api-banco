package br.com.apibanco.msextratoconsumer.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/**
 * Record idêntico ao do produtor para mapear o JSON recebido da rede.
 */
public record TransactionEventDTO(
                UUID transactionId,
                String accountSource,
                String accountDestination,
                BigDecimal amount,
                String type,
                String status,
                Instant timestamp) {
}
