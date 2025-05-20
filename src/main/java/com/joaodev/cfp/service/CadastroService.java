package com.joaodev.cfp.service;

import com.joaodev.cfp.DTO.UsuarioDTO;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.exceptions.ErrorResponse;
import com.joaodev.cfp.repository.FinanceiroRepository;
import com.joaodev.cfp.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CadastroService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FinanceiroRepository financeiroRepository;
    @Autowired
    private FinanceiroService financeiroService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CategoriaService categoriaService;

    public ResponseEntity<?> cadastro(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        if (usuarioService.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse("Email já cadastrado"));
        }

        Usuario novoUsuario = new Usuario();
        novoUsuario.setEmail(usuarioDTO.getEmail());
        novoUsuario.setNome(usuarioDTO.getNome());
        novoUsuario.setRole(usuarioDTO.getRole());

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
        novoUsuario.setSenha(senhaCriptografada);

        usuarioRepository.save(novoUsuario);
        financeiroService.createByUsuarioId(novoUsuario.getId());
        categoriaService.criarCategoriasPadrao(novoUsuario);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
