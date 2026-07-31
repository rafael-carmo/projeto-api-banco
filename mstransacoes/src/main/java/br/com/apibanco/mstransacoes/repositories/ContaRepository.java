package br.com.apibanco.mstransacoes.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.apibanco.mstransacoes.entities.Conta;

public interface ContaRepository extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumero(String numero);

    Optional<Conta> findByNumeroAndClienteId(String numero, Long idCliente);
}
