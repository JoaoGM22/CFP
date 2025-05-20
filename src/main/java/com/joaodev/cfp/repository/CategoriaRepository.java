package com.joaodev.cfp.repository;

import com.joaodev.cfp.entity.Categoria;
import com.joaodev.cfp.entity.Transacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface CategoriaRepository extends JpaRepository<Categoria,Long>, CrudRepository<Categoria,Long> {
   List<Categoria> findByUsuarioId(Long usuarioId);
   Categoria findByIdAndUsuarioId(Long id, Long idUsuario);

   @Modifying
   @Query("DELETE FROM Categoria c WHERE c.id = :id AND c.usuario.id = :usuarioId")
   void deleteByIdAndUsuarioId(@Param("id") Long id, @Param("usuarioId") Long usuarioId);
}
