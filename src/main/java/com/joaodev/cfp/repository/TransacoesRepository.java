package com.joaodev.cfp.repository;

import com.joaodev.cfp.entity.Transacoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TransacoesRepository extends JpaRepository<Transacoes,Long> {
    List<Transacoes> findByUsuarioId(Long usuarioId);
    Transacoes findByIdAndUsuarioId(Long id,Long idUsuario);
    List<Transacoes> findTop3ByUsuarioIdOrderByIdDesc(Long usuarioId);
    List<Transacoes> findByUsuarioIdOrderByDataDesc(Long usuarioId);
    List<Transacoes> findByDataBetweenAndUsuarioId(LocalDate dataInicio, LocalDate dataFim, Long usuarioId);
    List<Transacoes> findByDataAndUsuarioId(LocalDate data, Long usuarioId);


    @Modifying
    @Query("DELETE FROM Transacoes c WHERE c.id = :id AND c.usuario.id = :usuarioId")
    void deleteByIdAndUsuarioId(@Param("id") Long id, @Param("usuarioId") Long usuarioId);
}
