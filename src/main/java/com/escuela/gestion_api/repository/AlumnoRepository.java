package com.escuela.gestion_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.escuela.gestion_api.models.Alumno;


public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
    //para buscar por matricula
    Optional<Alumno> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
    boolean existsByEmail(String email);
}
