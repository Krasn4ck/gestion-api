package com.escuela.gestion_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GrupoRequestDTO(
        //La clave la generamos en automatico como servicio al crearse el grupo
        //@NotBlank(message = "La clave del grupo es obligatoria") @Size(max = 20, message = "La clave del grupo no debe de superar los 20 caracteres") String claveGrupo,
        // @NotBlank(message = "El periodo es obligatorio") @Size(max = 20, message =
        // "El periodo no debe de superar los 20 caracteres") String periodo,
        @Size(max = 50, message = "El aula no debe de superar los 50 caracteres") String aula,
        @NotNull(message = "El cupo es obligatorio") @Min(value = 1, message = "El cupo minimo es de un alumno") Integer cupoMaximo,
        @NotNull(message = "El id de la materia es obligatorio") Long materiaId,
        @NotNull(message = "El id del profesor es obligatorio") Long profesorId,
        @NotNull(message = "El id del periodo lectivo es obligatorio") Long periodoLectivoId // <-- Pasa el ID del
                                                                                             // PeriodoLectivo
) {
}