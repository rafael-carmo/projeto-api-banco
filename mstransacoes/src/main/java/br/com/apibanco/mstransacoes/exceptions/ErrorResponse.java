package br.com.apibanco.mstransacoes.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        List<FieldErrorDetails> fields // Opcional: detalha erros de validação (ex: @NotNull, @DecimalMin)
) {
    public ErrorResponse(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, null);
    }

}
