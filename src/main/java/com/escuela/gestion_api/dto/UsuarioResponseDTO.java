package com.escuela.gestion_api.dto;

import java.time.LocalDateTime;

import com.escuela.gestion_api.models.Rol;

public record UsuarioResponseDTO(
        Long id,
        String email,
        Boolean status,
        Rol rol,
        LocalDateTime fechaCreacion) {
}
