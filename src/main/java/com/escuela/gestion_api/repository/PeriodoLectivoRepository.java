package com.escuela.gestion_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.EstatusPeriodo;
import com.escuela.gestion_api.models.PeriodoLectivo;

public interface PeriodoLectivoRepository extends JpaRepository<PeriodoLectivo, Long> {
    Boolean existsByClave(String clave);

    Optional<PeriodoLectivo> findByClave(String clave);

    Optional<PeriodoLectivo> findByEstatus(EstatusPeriodo estatus);
}
