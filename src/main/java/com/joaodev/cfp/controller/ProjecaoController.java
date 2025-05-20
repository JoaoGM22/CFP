package com.joaodev.cfp.controller;

import com.joaodev.cfp.DTO.ProjecaoDTO;
import com.joaodev.cfp.DTO.ProjecaoDetalhadaDTO;
import com.joaodev.cfp.service.ProjecaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin("*")
public class ProjecaoController {

    @Autowired
    private ProjecaoService projecaoService;

    @GetMapping("/api/projecoes/saldo-diario")
    public ResponseEntity<List<ProjecaoDTO>> obterProjecaoDiaria(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim, Authentication authentication) {
        List<ProjecaoDTO> projecoes = projecaoService.calcularProjecaoDiaria(dataInicio, dataFim,authentication);
        return ResponseEntity.ok(projecoes);
    }

    @GetMapping("/api/projecoes/saldo-diario-detalhado")
    public ResponseEntity<List<ProjecaoDetalhadaDTO>> obterProjecaoDiariaDetalhada(
            @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim, Authentication authentication) {
        List<ProjecaoDetalhadaDTO> projecoesDetalhadas = projecaoService.calcularProjecaoDiariaDetalhada(dataInicio, dataFim, authentication);
        return ResponseEntity.ok(projecoesDetalhadas);
    }
}
