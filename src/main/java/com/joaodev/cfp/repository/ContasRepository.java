package com.joaodev.cfp.repository;

import com.joaodev.cfp.entity.Categoria;
import com.joaodev.cfp.entity.Contas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContasRepository  extends JpaRepository<Contas,Long>, CrudRepository<Contas,Long> {
    List<Contas> findByUsuarioId(Long usuarioId);
    Contas findByIdAndUsuarioId(Long id,Long idUsuario);

    @Modifying
    @Query("DELETE FROM Contas c WHERE c.id = :id AND c.usuario.id = :usuarioId")
    void deleteByIdAndUsuarioId(@Param("id") Long id, @Param("usuarioId") Long usuarioId);
}
