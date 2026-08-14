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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Transações", description = "Operações relacionadas a transações bancárias")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @Operation(summary = "Verifica o status do serviço de transações", description = "Retorna o status do serviço de transações bancárias, indicando se está operacional.")
    @ApiResponse(responseCode = "200", description = "Serviço operacional")
    @ApiResponse(responseCode = "503", description = "Serviço indisponível")
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
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
