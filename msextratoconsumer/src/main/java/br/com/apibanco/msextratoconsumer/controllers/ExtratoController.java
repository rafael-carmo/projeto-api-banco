package br.com.apibanco.msextratoconsumer.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.apibanco.msextratoconsumer.dtos.ExtratoResponseDTO;
import br.com.apibanco.msextratoconsumer.services.ExtratoService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ExtratoController {

    private final ExtratoService service;

    // Endpoint: GET /extratos/{numeroConta}
    @GetMapping("/{numeroConta}")
    public ResponseEntity<List<ExtratoResponseDTO>> obterExtrato(@PathVariable String numeroConta) {
        List<ExtratoResponseDTO> extrato = service.obterExtratoPorConta(numeroConta);

        // Se não houver movimentações, retorna uma lista vazia com status 200 OK
        return ResponseEntity.ok(extrato);
    }
}
