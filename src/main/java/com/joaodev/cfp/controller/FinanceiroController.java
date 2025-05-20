package com.joaodev.cfp.controller;

import com.joaodev.cfp.DTO.FinanceiroDTO;
import com.joaodev.cfp.entity.Financeiro;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.repository.FinanceiroRepository;
import com.joaodev.cfp.service.FinanceiroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class FinanceiroController {

    @Autowired
    private FinanceiroService financeiroService;

    @GetMapping("/api/configuracoes")
    public ResponseEntity<FinanceiroDTO> getHomeData(Authentication authentication) {
        return financeiroService.getHomeData(authentication);
    }

    @PutMapping("/api/configuracoes")
    public ResponseEntity<String> update(Authentication authentication, @RequestBody FinanceiroDTO financeiro){
        return financeiroService.atualizarFinanceiro(authentication, financeiro);
    }
}
