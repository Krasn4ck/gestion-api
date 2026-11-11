package com.escuela.gestion_api.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record InscripcionResquestDTO(
        @NotNull(message = "El ID del alumno es obligatorio") Long alumnoId,
        @NotNull(message = "El ID del grupo es obligatorio") Long grupoId,
        BigDecimal montoInscripcion, // es opcional si el servicio asigna el monto base
        @NotNull(message = "El periodo lectivo es obligatorio") Long periodoLectivoId) {
}
