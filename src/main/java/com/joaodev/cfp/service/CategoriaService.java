package com.joaodev.cfp.service;

import com.joaodev.cfp.entity.Categoria;
import com.joaodev.cfp.entity.Transacoes;
import com.joaodev.cfp.entity.Usuario;
import com.joaodev.cfp.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Categoria> findCategoriasByUsuarioId(Long id){
        return categoriaRepository.findByUsuarioId(id);
    }

    public ResponseEntity<?> criarCategoriaById(Categoria categoria,Long id){
        Optional<Usuario> usuario = usuarioService.findById(id);
        Categoria novaCategoria = new Categoria();
        novaCategoria.setNome(categoria.getNome());
        novaCategoria.setUsuario(usuario.get());
        try {
            createByUsuarioId(novaCategoria);
            return new ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> deleteCategoria(Long id,Long usuarioId) {
        try {
            categoriaRepository.deleteByIdAndUsuarioId(id,usuarioId);
            return new ResponseEntity<>("Deletado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    private Categoria createByUsuarioId(Categoria novaCategoria) {
        return categoriaRepository.save(novaCategoria);
    }

    public Categoria findById(@PathVariable Long idUsuario,@PathVariable Long id){
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(id,idUsuario);
        return categoria;
    }

    public ResponseEntity<?> atualizarCategoria(Long idUsuario, Categoria categoriaAtualizada){
        Categoria categoria = categoriaRepository.findByIdAndUsuarioId(categoriaAtualizada.getId(), idUsuario);
        categoria.setNome(categoriaAtualizada.getNome());
        try {
            categoriaRepository.save(categoria);
            return new ResponseEntity<>("Editado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erro!" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    public void criarCategoriasPadrao(Usuario usuario){
        List<Categoria> categorias = new ArrayList<>();
        Categoria moradia = new Categoria("Moradia",usuario);
        Categoria alimentacao = new Categoria("Alimentação", usuario);
        Categoria transporte = new Categoria("Transporte", usuario);
        Categoria lazer = new Categoria("Lazer", usuario);
        Categoria outros = new Categoria("Outros", usuario);
        categorias.add(moradia);
        categorias.add(alimentacao);
        categorias.add(transporte);
        categorias.add(lazer);
        categorias.add(outros);

        for(Categoria categoria : categorias){
            categoriaRepository.save(categoria);
        }
    }
}
