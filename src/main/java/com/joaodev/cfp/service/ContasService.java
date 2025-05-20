package com.joaodev.cfp.service;

import com.joaodev.cfp.DTO.ContasDTO;
import com.joaodev.cfp.DTO.FinanceiroDTO;
import com.joaodev.cfp.entity.Contas;
import com.joaodev.cfp.entity.Financeiro;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class ContasService {

    @Autowired
    private ContasRepository contasRepository;

    @Autowired
    private UsuarioService usuarioService;

    public ContasService(ContasRepository contasRepository) {
        this.contasRepository = contasRepository;
    }

    private ContasDTO convertToDTO(Contas contas) {
        ContasDTO dto = new ContasDTO();
        dto.setValor(contas.getValor());
        dto.setData(contas.getData());
        dto.setDescricao(contas.getDescricao());
        dto.setUsuario(contas.getUsuario());
        return dto;
    }

    private Contas convertToContas(ContasDTO dto) {
        Contas contas = new Contas();
        contas.setValor(dto.getValor());
        contas.setData(dto.getData());
        contas.setDescricao(dto.getDescricao());
        contas.setUsuario(dto.getUsuario());
        return contas;
    }

    private Contas createByUsuarioId(ContasDTO novaConta){
        return contasRepository.save(convertToContas(novaConta));
    }

    public List<Contas> ListaByUsuarioId(Long usuarioId){
        return contasRepository.findByUsuarioId(usuarioId);
    }

    public ResponseEntity<String> novaConta(Contas contas, Authentication authentication){
        String email = authentication.getName();
        Optional<Usuario> usuario = usuarioService.findByEmail(email);

        ContasDTO novaConta = new ContasDTO();
        novaConta.setValor(contas.getValor());
        novaConta.setDescricao(contas.getDescricao());
        novaConta.setData(contas.getData());
        novaConta.setUsuario(usuario.get());
        try {
            createByUsuarioId(novaConta);
            return new ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> atualizarConta(Contas contaAtualizada, Long idUsuario) {
        Contas contas = contasRepository.findByIdAndUsuarioId(contaAtualizada.getId(),idUsuario);
        contas.setValor(contaAtualizada.getValor());
        contas.setDescricao(contaAtualizada.getDescricao());
        contas.setData(contaAtualizada.getData());
        try {
            contasRepository.save(contas);
            return new ResponseEntity<>("Editado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteByIdAndUsuarioId(Long id,Long usuarioId){
        try {
            contasRepository.deleteByIdAndUsuarioId(id,usuarioId);
            return new ResponseEntity<>("Deletado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
