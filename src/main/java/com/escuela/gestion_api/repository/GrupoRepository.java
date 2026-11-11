package com.escuela.gestion_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.Grupo;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {

    long countByMateriaIdAndPeriodoLectivoClave(Long materiaId, String clavePeriodo);

    List<Grupo> findByPeriodoLectivo(String periodoLectivo);

    List<Grupo> findByProfesor(Long profesorId);

}
