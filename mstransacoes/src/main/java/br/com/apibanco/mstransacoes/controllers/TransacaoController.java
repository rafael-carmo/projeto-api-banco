package br.com.apibanco.mstransacoes.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apibanco.mstransacoes.dtos.DepositoDTO;
import br.com.apibanco.mstransacoes.dtos.SaqueDTO;
import br.com.apibanco.mstransacoes.dtos.ComprovanteDepositoDTO;
import br.com.apibanco.mstransacoes.dtos.ComprovanteSaqueDTO;
import br.com.apibanco.mstransacoes.dtos.TransferenciaDTO;
import br.com.apibanco.mstransacoes.dtos.ComprovanteTransferenciaDTO;
import br.com.apibanco.mstransacoes.services.TransacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("Status do serviço de transações: OK");
    }

    @PostMapping("/deposito")
    public ResponseEntity<ComprovanteDepositoDTO> depositar(@RequestBody @Valid DepositoDTO request) {
        var comprovanteDeposito = transacaoService.depositar(request);
        return ResponseEntity.ok(comprovanteDeposito);
    }

    @PostMapping("/saque")
    public ResponseEntity<ComprovanteSaqueDTO> sacar(@RequestBody @Valid SaqueDTO request) {
        var comprovanteSaque = transacaoService.sacar(request);
        return ResponseEntity.ok(comprovanteSaque);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<ComprovanteTransferenciaDTO> transferir(
            @RequestBody @Valid TransferenciaDTO request) {
        var response = transacaoService.transferir(request);
        return ResponseEntity.ok(response);
    }
}
