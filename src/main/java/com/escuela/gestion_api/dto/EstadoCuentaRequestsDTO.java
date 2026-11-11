package com.escuela.gestion_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EstadoCuentaRequestsDTO(
        @NotNull(message = "El id del alumno es obligatorio") Long alumnoId,
        @NotNull(message = "El id del periodo lectivo es obligatorio") Long peridoLectivoId,
        @NotBlank(message = "El concepto es obligatorio") String concepto,
        @NotNull(message = "El monto es obligatorio") @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0") BigDecimal monto,
        @NotNull(message = "La fecha de vencimiento es obligatoria") LocalDate fechaVencimiento) {

}
