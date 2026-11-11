package com.escuela.gestion_api.dto;

public record MateriaResponseDTO(
        Long id,
        String Clave,
        String Nombre,
        Integer Creditos) {
}
