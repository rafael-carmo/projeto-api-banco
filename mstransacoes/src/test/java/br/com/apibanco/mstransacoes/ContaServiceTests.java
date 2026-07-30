package br.com.apibanco.mstransacoes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.apibanco.mstransacoes.dtos.ContaMovimentacaoDTO;
import br.com.apibanco.mstransacoes.dtos.ContaResponseDTO;
import br.com.apibanco.mstransacoes.entities.Conta;
import br.com.apibanco.mstransacoes.enums.TipoConta;
import br.com.apibanco.mstransacoes.repositories.ContaRepository;
import br.com.apibanco.mstransacoes.services.ContaService;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTests {

    @Mock
    private ContaRepository repository;

    @InjectMocks
    private ContaService service;

    private Conta contaOrigem;
    private Conta contaDestino;

    @BeforeEach
    void setUp() {
        // Inicializa contas de teste usando o construtor da Entidade (ID_CLIENTE,
        // NUMERO, TIPO, SALDO)
        contaOrigem = new Conta(1L, 1L, "12345", LocalDateTime.now(), LocalDateTime.now(), TipoConta.CORRENTE,
                new BigDecimal("1000.00"));
        contaDestino = new Conta(2L, 2L, "67890", LocalDateTime.now(), LocalDateTime.now(), TipoConta.POUPANCA,
                new BigDecimal("500.00"));
    }

    @Test
    @DisplayName("Deve realizar depósito com sucesso")
    void deveRealizarDepositoComSucesso() {
        // Arrange
        ContaMovimentacaoDTO request = new ContaMovimentacaoDTO(null, "12345", new BigDecimal("250.50"));
        when(repository.findByNumero("12345")).thenReturn(Optional.of(contaOrigem));

        // Act
        Conta conta = service.depositar(request);

        ContaResponseDTO response = new ContaResponseDTO(conta);

        // Assert
        // Nota: compareTo == 0 garante a igualdade de valores do BigDecimal sem falhar
        // por diferenças de escala (ex: 1250.5 vs 1250.50)
        assertEquals(0, new BigDecimal("1250.50").compareTo(response.saldo()));
        verify(repository, times(1)).save(contaOrigem);
    }
}
