package com.joaodev.cfp.controller;

import com.joaodev.cfp.entity.Categoria;
import com.joaodev.cfp.service.CategoriaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/api/categoria/{id}")
    public List<Categoria> listaCategorias(@PathVariable Long id){
        return categoriaService.findCategoriasByUsuarioId(id);
    }

    @GetMapping("/api/categoria/{idUsuario}/{id}")
    public Categoria findByid(@PathVariable Long idUsuario,@PathVariable Long id){
        return categoriaService.findById(idUsuario,id);
    }

    @PostMapping("/api/categoria/{id}")
    public ResponseEntity<?> criarCategoria(@RequestBody Categoria categoria,@PathVariable Long id){
        return categoriaService.criarCategoriaById(categoria, id);
    }

    @PutMapping("/api/categoria/{idUsuario}")
    public ResponseEntity<?> atualizaCategoira(@PathVariable Long idUsuario,@RequestBody Categoria categoria){
        return categoriaService.atualizarCategoria(idUsuario,categoria);
    }

    @DeleteMapping("/api/categoria/{usuarioId}/{id}")
    @Transactional
    public ResponseEntity<String> deleteCategoria(@PathVariable Long id, @PathVariable Long usuarioId) {
        return categoriaService.deleteCategoria(id,usuarioId);
    }

}
