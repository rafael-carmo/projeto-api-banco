package br.com.apibanco.msextratoconsumer.exceptions;

import java.time.Instant;

public record ErrorMessage(
        int status,
        Instant timestamp,
        String message,
        String description) {
}
