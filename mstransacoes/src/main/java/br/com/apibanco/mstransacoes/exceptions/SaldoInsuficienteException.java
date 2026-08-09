package br.com.apibanco.mstransacoes.exceptions;

public class SaldoInsuficienteException extends RuntimeException {
    private final String message;

    public SaldoInsuficienteException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
