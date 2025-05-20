package com.joaodev.cfp.service;

import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.exceptions.UsuarioNotFoundException;
import com.joaodev.cfp.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findById(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> findByEmail(String email){
        return usuarioRepository.findByEmail(email);
    }

    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarUm(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
    }

    public Usuario novoUsuario(@RequestBody Usuario novoUsuario) {
        return usuarioRepository.save(novoUsuario);
    }

    public Usuario replaceUsuario(@RequestBody Usuario novoUsuario, @PathVariable Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setNome(novoUsuario.getNome());
            return usuarioRepository.save(novoUsuario);
        }).orElseGet(() -> {
            return usuarioRepository.save(novoUsuario);
        });
    }

    @Transactional
    public void deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

}
