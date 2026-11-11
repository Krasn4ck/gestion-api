package com.escuela.gestion_api.dto;

import java.time.LocalDate;

import com.escuela.gestion_api.models.EstatusPeriodo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PeriodoLectivoRequestsDTO(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotNull(message = "El año es obligatorio") Integer anio,
        @NotNull(message = "La fecha de inicio es obligatoria") LocalDate fechaInicio,
        @NotNull(message = "la fecha de termino es obligatoria") LocalDate fechaFin,
        @NotNull(message = "La fecha limite de inscripcion es obligatoria") LocalDate fechaLimiteInscripcion,
        @NotNull(message = "El estatus del periodo es obligatorio") EstatusPeriodo estatus) {

}
