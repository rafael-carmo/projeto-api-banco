package br.com.apibanco.mstransacoes.exceptions;

public class ContaNotFoundException extends RuntimeException {
    private final String message;

    public ContaNotFoundException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
