package com.joaodev.cfp.repository;

import com.joaodev.cfp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario,Long>, JpaRepository<Usuario,Long> {

    Optional<Usuario> findByEmail(String email);

}
