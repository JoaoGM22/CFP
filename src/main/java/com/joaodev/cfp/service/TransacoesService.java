package com.joaodev.cfp.service;

import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.TransacoesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service
public class TransacoesService {

    @Autowired
    private TransacoesRepository transacoesRepository;
    @Autowired
    private UsuarioService usuarioService;

    public TransacoesService(TransacoesRepository transacoesRepository) {
        this.transacoesRepository = transacoesRepository;
    }

    public ResponseEntity<String> deleteByIdAndUsuarioId(Long id, Long usuarioId) {
        try {
            transacoesRepository.deleteByIdAndUsuarioId(id, usuarioId);
            return new ResponseEntity<>("Deletado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> atualizarTransacao(Long idUsuario, Transacoes transacaoAtualizada) {
        Transacoes transacao = transacoesRepository.findByIdAndUsuarioId(transacaoAtualizada.getId(), idUsuario);
        transacao.setValor(transacaoAtualizada.getValor());
        transacao.setCategoria(transacaoAtualizada.getCategoria());
        transacao.setDescricao(transacaoAtualizada.getDescricao());
        transacao.setData(transacaoAtualizada.getData());
        transacao.setParcelas(transacaoAtualizada.getParcelas());
        transacao.setFixo(transacaoAtualizada.getFixo());
        transacao.setTipo(transacaoAtualizada.getTipo());

        try {
            transacoesRepository.save(transacao);
            return new ResponseEntity<>("Editado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public List<Transacoes> getTransacoesRecentesByUsuarioId(Long usuarioId) {
        return transacoesRepository.findTop3ByUsuarioIdOrderByIdDesc(usuarioId);
    }

    public ResponseEntity<String> novaTrasacao(@PathVariable Long id, @RequestBody Transacoes transacao) {
        Optional<Usuario> usuario = usuarioService.findById(id);
        Transacoes novaTransacao = new Transacoes();
        novaTransacao.setUsuario(usuario.get());
        novaTransacao.setCategoria(transacao.getCategoria());
        novaTransacao.setData(transacao.getData());
        novaTransacao.setDescricao(transacao.getDescricao());
        novaTransacao.setParcelas(transacao.getParcelas());
        novaTransacao.setFixo(transacao.getFixo());
        novaTransacao.setValor(transacao.getValor());
        novaTransacao.setTipo(transacao.getTipo());
        try {
            createByUsuarioId(novaTransacao);
            return new ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    private Transacoes createByUsuarioId(Transacoes novaTransacao) {
        return transacoesRepository.save(novaTransacao);
    }

    public Transacoes findById(@PathVariable Long idUsuario,@PathVariable Long id){
        Transacoes transacao = transacoesRepository.findByIdAndUsuarioId(id,idUsuario);
        return transacao;
    }

    public List<Transacoes> todasTransacoes(@PathVariable Long id){
        List<Transacoes> listaTransacoes = transacoesRepository.findByUsuarioIdOrderByDataDesc(id);
        return listaTransacoes;
    }

}
