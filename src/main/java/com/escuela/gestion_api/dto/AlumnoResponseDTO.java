package com.escuela.gestion_api.dto;

import java.time.LocalDate;

import com.escuela.gestion_api.models.NivelEducativo;

public record AlumnoResponseDTO(
        Long Id,
        String matricula,
        String nombre,
        String ApellidoP,
        String ApellidoM,
        String email,
        LocalDate fechaNacimiento,
        NivelEducativo nivelEducativo,
        String referenciaBancaria) {

}
