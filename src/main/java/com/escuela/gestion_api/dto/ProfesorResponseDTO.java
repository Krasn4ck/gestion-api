package com.escuela.gestion_api.dto;

public record ProfesorResponseDTO(
        Long id,
        String numeroEmpleado,
        String nombre,
        String apellidoPaterno,
        String apellidoMaterno,
        String cubiculo){
}
