package br.com.apibanco.mstransacoes.exceptions;

public record FieldErrorDetails(
                String field,
                String message) {

}
