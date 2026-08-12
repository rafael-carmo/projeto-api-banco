package br.com.apibanco.msextratoconsumer.exceptions;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata erros de comunicação ou queda do banco Cassandra
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ResponseEntity<ErrorMessage> handleCassandraDownException(DataAccessResourceFailureException ex,
            WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                Instant.now(),
                "O serviço de extratos está temporariamente indisponível. Falha na comunicação com o banco de dados.",
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.SERVICE_UNAVAILABLE);
    }

    // Trata qualquer outro erro inesperado na aplicação (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGlobalException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                Instant.now(),
                "Ocorreu um erro interno no servidor ao processar o extrato.",
                request.getDescription(false));
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
