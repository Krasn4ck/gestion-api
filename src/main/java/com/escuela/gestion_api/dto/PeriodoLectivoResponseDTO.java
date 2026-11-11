package com.escuela.gestion_api.dto;

import java.time.LocalDate;

import com.escuela.gestion_api.models.EstatusPeriodo;

public record PeriodoLectivoResponseDTO(
        Long id,
        String clave,
        String nombre,
        Integer anio,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        LocalDate fechaLimiteInscripcion,
        EstatusPeriodo estatus) {
}
