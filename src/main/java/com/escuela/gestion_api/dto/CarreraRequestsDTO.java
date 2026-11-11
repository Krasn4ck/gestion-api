package com.escuela.gestion_api.dto;

import com.escuela.gestion_api.models.NivelEducativo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarreraRequestsDTO(
        @NotBlank(message = "La clave es obligatoria") @Size(max = 10) String clave,
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 150) String nombre,
        @NotNull(message = "El nivel educativo es obligatorio") NivelEducativo nivelEducativo) {
}
