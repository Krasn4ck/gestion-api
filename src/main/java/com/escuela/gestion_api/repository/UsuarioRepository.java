package com.escuela.gestion_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
