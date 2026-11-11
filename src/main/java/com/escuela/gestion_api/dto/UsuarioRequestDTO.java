package com.escuela.gestion_api.dto;

import com.escuela.gestion_api.models.Rol;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioRequestDTO(
        @NotBlank(message = "El email es obligatorio") @Email(message = "Debe de ser un email valido") @Size(max = 120) String email,
        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres") String password,
        @NotNull(message = "El rol es obligatorio") Rol rol) {
}
