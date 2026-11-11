package com.escuela.gestion_api.dto;

import java.time.LocalDateTime;

import com.escuela.gestion_api.models.EstadoInscripcion;

public record InscripcionResponseDTO(
        Long id,
        Long alumnoId,
        String nombreAlumno,
        String matriculaAlumno,
        Long grupoId,
        String claveGrupo,
        String nombreMateria,
        Double calificacionFinal,
        EstadoInscripcion estadoInscripcion,
        LocalDateTime fechaInscripcion,
        String clavePeriodoLectivo) {
}
