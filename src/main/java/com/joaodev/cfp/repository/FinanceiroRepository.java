package com.joaodev.cfp.repository;

import com.joaodev.cfp.entity.Financeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FinanceiroRepository extends CrudRepository<Financeiro,Long>,JpaRepository<Financeiro,Long> {
    Financeiro findByUsuarioId(Long usuarioId);
    // Atualiza o saldo de um usuário específico, somando o valor do salário
    @Modifying
    @Query("UPDATE Financeiro f SET f.saldo = f.saldo + f.salario WHERE f.usuario.id = :usuarioId")
    void atualizarSaldoPorUsuarioId(@Param("usuarioId") Long usuarioId);

    // Busca todos os IDs de usuários distintos
    @Query("SELECT DISTINCT f.usuario.id FROM Financeiro f")
    List<Long> findAllDistinctUsuarioIds();
}
