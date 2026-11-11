package com.escuela.gestion_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.Profesor;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    List<Profesor> findByNumeroEmpleado(String numeroEmpleado);

    Boolean existsByNumeroEmpleado(String numeroEmpleado);
}
