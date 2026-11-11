package com.escuela.gestion_api.dto;

public record GrupoResponseDTO(
                Long Id,
                String claveGrupo,
                String aula,
                Integer cupoMaximo,
                Integer cupoDisponible,
                String nombreMateria,
                String nombreCarrera,
                String nombreProfesor,
                String clavePeriodo,
                String nombrePeriodo) {
}
