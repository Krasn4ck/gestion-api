package com.escuela.gestion_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfesorRequestDTO(
                @NotBlank(message = "El nombre es obligatorio") @Size(max = 100) String nombre,
                @NotBlank(message = "El apellido paterno es obligatorio") @Size(max = 100) String apellidoPaterno,
                @Size(max = 100) String apellidoMaterno,
                @Size(max = 100) String cubiculo
                //@NotBlank(message = "El usuario Id es necesario para relacionarlo")
) {
}
