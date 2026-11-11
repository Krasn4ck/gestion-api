package com.escuela.gestion_api.dto;

import com.escuela.gestion_api.models.NivelEducativo;

public record CarreraResponseDTO(
        Long id,
        String clave,
        String nombre,
        NivelEducativo nivelEducativo) {

}
