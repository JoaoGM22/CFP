package com.joaodev.cfp.controller;

import com.joaodev.cfp.DTO.FinanceiroDTO;
import com.joaodev.cfp.entity.Financeiro;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.UsuarioRepository;
import com.joaodev.cfp.service.FinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private FinanceiroService financeiroService;

    @GetMapping("/home")
    public ResponseEntity<FinanceiroDTO> getHomeData(Authentication authentication) {
        return financeiroService.getHomeData(authentication);
    }

}
