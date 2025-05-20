package com.joaodev.cfp.controller;

import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.CategoriaRepository;
import com.joaodev.cfp.repository.TransacoesRepository;
import com.joaodev.cfp.repository.UsuarioRepository;
import com.joaodev.cfp.service.TransacoesService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class TransacoesController {

    @Autowired
    private TransacoesService transacoesService;

    @GetMapping("/api/transacoes/{id}")
    public List<Transacoes> todasTransacoes(@PathVariable Long id){
       return transacoesService.todasTransacoes(id);
    }

    @GetMapping("/api/transacoes/{idUsuario}/{id}")
    public Transacoes findById(@PathVariable Long idUsuario,@PathVariable Long id){
        return transacoesService.findById(idUsuario,id);
    }

    @PostMapping("/api/transacoes/{id}")
    public ResponseEntity<String> novaTrasacao(@PathVariable Long id, @RequestBody Transacoes transacao){
       return transacoesService.novaTrasacao(id,transacao);
    }

    @PutMapping("/api/transacoes/{idUsuario}")
    public ResponseEntity<String> update(@PathVariable Long idUsuario, @RequestBody Transacoes transacao){
       return transacoesService.atualizarTransacao(idUsuario, transacao);
    }

    @DeleteMapping("/api/transacoes/{usuarioId}/{id}")
    @Transactional
    public ResponseEntity<String> deleteTransacao(@PathVariable Long id, @PathVariable Long usuarioId) {
        return transacoesService.deleteByIdAndUsuarioId(id,usuarioId);
    }

}
