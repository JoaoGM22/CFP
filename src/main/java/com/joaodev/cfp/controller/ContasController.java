package com.joaodev.cfp.controller;

import com.joaodev.cfp.DTO.ContasDTO;
import com.joaodev.cfp.entity.Contas;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.service.ContasService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class ContasController {

    @Autowired
    private ContasService contasService;

    @GetMapping("/api/contas/{id}")
    public List<Contas> ListaByUsuarioId(@PathVariable Long id){
        return contasService.ListaByUsuarioId(id);
    }

    @PostMapping("/api/contas/{id}")
    public ResponseEntity<String> novaConta(@RequestBody Contas contas, Authentication authentication){
        return contasService.novaConta(contas,authentication);
    }

    @PutMapping("/api/contas/{idUsuario}")
    public ResponseEntity<String> update(@PathVariable Long idUsuario, @RequestBody Contas contas){
        return contasService.atualizarConta(contas,idUsuario);
    }

    @DeleteMapping("/api/contas/{usuarioId}/{id}")
    @Transactional
    public ResponseEntity<String> deleteTransacao(@PathVariable Long id, @PathVariable Long usuarioId) {
        return contasService.deleteByIdAndUsuarioId(id,usuarioId);
    }

}
