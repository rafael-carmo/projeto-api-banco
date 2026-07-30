package br.com.apibanco.mstransacoes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apibanco.mstransacoes.dtos.ContaMovimentacaoDTO;
import br.com.apibanco.mstransacoes.dtos.ContaResponseDTO;
import br.com.apibanco.mstransacoes.dtos.ContaTransferenciaDTO;
import br.com.apibanco.mstransacoes.entities.Conta;
import br.com.apibanco.mstransacoes.services.ContaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;

    @GetMapping
    public ResponseEntity<String> connect() {
        return ResponseEntity.ok("teste controller");
    }

    @PostMapping("/deposito")
    public ResponseEntity<ContaResponseDTO> depositar(@RequestBody @Valid ContaMovimentacaoDTO request) {
        Conta conta = contaService.depositar(request);
        var contaResponse = new ContaResponseDTO(conta);
        return ResponseEntity.ok(contaResponse);
    }

    @PostMapping("/saque")
    public ResponseEntity<ContaResponseDTO> sacar(@RequestBody @Valid ContaMovimentacaoDTO request) {
        Conta conta = contaService.sacar(request);
        var contaResponse = new ContaResponseDTO(conta);
        return ResponseEntity.ok(contaResponse);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> transferir(@RequestBody @Valid ContaTransferenciaDTO request) {
        contaService.transferir(request);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content se der tudo certo
    }
}
