package br.com.apibanco.mstransacoes.dtos;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionEventDTO(
    UUID transactionId,
    String accountSource,
    String accountDestination,
    BigDecimal amount,
    String type, //"TRANSFER", "DEPOSIT" ou "WITHDRAW",
    String status, //"COMPLETED", "FAILED" ou "PENDING"
    Instant timestamp
) {
    
}
