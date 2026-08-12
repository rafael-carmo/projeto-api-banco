package br.com.apibanco.msextratoconsumer.repositories;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;

import br.com.apibanco.msextratoconsumer.entities.TransactionEntity;

public interface TransactionRepository extends CassandraRepository<TransactionEntity, String> {

    // O Spring Data gera a query automática: SELECT * FROM transactions_by_account
    // WHERE account_id = ?
    // Como definimos a ordenação no mapeamento da Entidade, o Cassandra já trará do
    // mais recente para o mais antigo.
    List<TransactionEntity> findAllByAccountId(String accountId);
}
