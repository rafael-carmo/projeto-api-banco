package br.com.apibanco.msextratoconsumer.services;

import org.springframework.stereotype.Service;

import br.com.apibanco.msextratoconsumer.dtos.ExtratoResponseDTO;
import br.com.apibanco.msextratoconsumer.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExtratoService {

    private final TransactionRepository repository;

    public List<ExtratoResponseDTO> obterExtratoPorConta(String numeroConta) {
        // Busca do Cassandra e transforma a lista de Entidades em uma lista de DTOs
        // usando Stream
        return repository.findAllByAccountId(numeroConta)
                .stream()
                .map(entity -> new ExtratoResponseDTO(
                        entity.getTransactionId(),
                        entity.getType(),
                        entity.getAmount(),
                        entity.getAccountDestination(),
                        entity.getStatus(),
                        entity.getTransactionTime()))
                .collect(Collectors.toList());
    }
}
