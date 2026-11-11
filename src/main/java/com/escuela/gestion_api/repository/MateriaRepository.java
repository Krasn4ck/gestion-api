package com.escuela.gestion_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
    Optional<Materia> findByClave(String clave);

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByClave(String clave);
}
