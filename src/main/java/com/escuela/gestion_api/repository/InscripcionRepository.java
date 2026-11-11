package com.escuela.gestion_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.EstadoInscripcion;
import com.escuela.gestion_api.models.Inscripcion;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    List<Inscripcion> findByAlumnoId(long alumnoId);

    List<Inscripcion> findByGrupoId(long grupoId);

    long countByGrupoIdAndEstadoInscripcion(Long grupoId, EstadoInscripcion estadoInscripcion);

    long countByAlumnoIdAndEstadoInscripcion(Long alumnoId, EstadoInscripcion estadoInscripcion);

    boolean existsByAlumnoIdAndGrupoPeriodoLectivoId(Long alumnoId, Long periodoLectivoId);
}
