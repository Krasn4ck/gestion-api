package com.escuela.gestion_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MateriaRequestDTO(
                //@NotBlank(message = "La clave de la materia es obligatoria") @Size(max = 10, message = "La clave no puede exceder 10 caracteres") String clave,
                @NotBlank(message = "El nombre de la materia es obligatorio") @Size(max = 100, message = "El nombre no puede exceder 100 caracteres") String nombre,
                @NotNull(message = "Los créditos son obligatorios") @Min(value = 1, message = "La materia debe tener al menos 1 crédito") @Max(value = 20, message = "La materia no puede tener más de 20 créditos") Integer creditos
                //@NotNull (message = "El Id de la carrera es obligatorio") Long carreraId
                )
                {
}
