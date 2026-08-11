package br.com.apibanco.msextratoconsumer.repositories;

import org.springframework.data.cassandra.repository.CassandraRepository;

import br.com.apibanco.msextratoconsumer.entities.TransactionEntity;

public interface TransactionRepository extends CassandraRepository<TransactionEntity, String> {

}
