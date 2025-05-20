package com.joaodev.cfp.controller;

import com.joaodev.cfp.DTO.UsuarioDTO;
import com.joaodev.cfp.entity.Financeiro;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.FinanceiroRepository;
import com.joaodev.cfp.repository.UsuarioRepository;
import com.joaodev.cfp.service.CadastroService;
import com.joaodev.cfp.service.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class CadastroController {

    @Autowired
    private CadastroService cadastroService;

    @PostMapping("/api/cadastrar")
    public ResponseEntity<?> cadastro(@RequestBody UsuarioDTO novoUsuario) {
        return cadastroService.cadastro(novoUsuario);
    }
}
