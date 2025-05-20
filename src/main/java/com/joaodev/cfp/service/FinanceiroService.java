package com.joaodev.cfp.service;

import com.joaodev.cfp.DTO.FinanceiroDTO;
import com.joaodev.cfp.entity.Contas;
import com.joaodev.cfp.entity.Financeiro;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.FinanceiroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class FinanceiroService {

    @Autowired
    private FinanceiroRepository financeiroRepository;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private TransacoesService transacoesService;
    @Autowired
    private ContasService contasService;

    private FinanceiroDTO convertToDTO(Financeiro financeiro) {
        FinanceiroDTO dto = new FinanceiroDTO();
        dto.setSaldo(financeiro.getSaldo());
        dto.setSalario(financeiro.getSalario());
        dto.setRendaMensal(financeiro.getRendaMensal());
        dto.setDespesaMensal(financeiro.getDespesaMensal());
        return dto;
    }

    public Financeiro findByUsuarioId(Long id) {
        return financeiroRepository.findByUsuarioId(id);
    }

    @Transactional
    public Financeiro createByUsuarioId(Long usuarioId) {
        Optional<Usuario> usuario = usuarioService.findById(usuarioId);
        Financeiro novoFinanceiro = new Financeiro();
        novoFinanceiro.setUsuario(usuario.orElse(null));
        return financeiroRepository.save(novoFinanceiro);
    }

    public void atualizarSaldoParaTodosUsuarios() {
        List<Long> usuariosIds = financeiroRepository.findAllDistinctUsuarioIds();
        for (Long usuarioId : usuariosIds) {
            financeiroRepository.atualizarSaldoPorUsuarioId(usuarioId);
        }
    }

    public ResponseEntity<FinanceiroDTO> getHomeData(Authentication authentication) {
        String email = authentication.getName();
        Optional<Usuario> usuario = usuarioService.findByEmail(email);

        FinanceiroDTO financeiro = convertToDTO(findByUsuarioId(usuario.get().getId()));
        financeiro.setUsuario(usuario.get());
        financeiro.setTransacoesRecentes(transacoesService.getTransacoesRecentesByUsuarioId(usuario.get().getId()));
        financeiro.setTransacoes(transacoesService.todasTransacoes(usuario.get().getId()));
        financeiro.setContas(contasService.ListaByUsuarioId(usuario.get().getId()));


        return ResponseEntity.ok(financeiro);
    }

    public ResponseEntity<String> atualizarFinanceiro(Authentication authentication, FinanceiroDTO financeiroAtualizado) {
        String email = authentication.getName();
        Optional<Usuario> usuario = usuarioService.findByEmail(email);

        Financeiro financeiro = findByUsuarioId(usuario.get().getId());
        financeiro.setSaldo(financeiroAtualizado.getSaldo());
        financeiro.setSalario(financeiroAtualizado.getSalario());
        System.out.println(financeiro.toString());

        try {
            financeiroRepository.save(financeiro);
            return new ResponseEntity<>("Editado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
