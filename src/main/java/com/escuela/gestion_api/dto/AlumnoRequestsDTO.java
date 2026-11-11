package com.escuela.gestion_api.dto;

import java.time.LocalDate;

import com.escuela.gestion_api.models.NivelEducativo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AlumnoRequestsDTO(
                @NotBlank(message = "El nombre es obligatorio") @Size(max = 100, message = "El nombre no debe de exceder los 100 caracteres") String nombre,
                @NotBlank(message = "El apellido paterno es obligatorio") @Size(max = 100) String ApellidoP,
                @NotBlank(message = "El apellido materno es obligatorio") @Size(max = 100) String ApellidoM,
                @NotBlank(message = "El email es obligatorio para notificaciones") @Email(message = "El formato de email no es valido") String email,
                @NotNull(message = "La fecha de nacimiento es obligatoria") LocalDate fechaNacimiento,
                @NotNull(message = "El nivel educativo en obligatorio") NivelEducativo nivelEducativo)
                { }
