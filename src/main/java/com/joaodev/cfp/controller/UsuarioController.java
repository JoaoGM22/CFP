package com.joaodev.cfp.controller;

import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.exceptions.UsuarioNotFoundException;
import com.joaodev.cfp.repository.UsuarioRepository;
import com.joaodev.cfp.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/api/usuarios")
    public List<Usuario> buscarTodos() {
        return usuarioService.buscarTodos();
    }

    @GetMapping("/usuarios/{id}")
    public Usuario buscarUm(@PathVariable Long id) {
        return usuarioService.buscarUm(id);
    }

    @PostMapping("/api/usuarios")
    public Usuario novoUsuario(@RequestBody Usuario novoUsuario) {
        return usuarioService.novoUsuario(novoUsuario);
    }

    @PutMapping("/api/usuarios/{id}")
    Usuario replaceUsuario(@RequestBody Usuario novoUsuario, @PathVariable Long id) {
        return usuarioService.replaceUsuario(novoUsuario, id);
    }

    @DeleteMapping("/api/usuarios/{id}")
    void deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
    }


}
