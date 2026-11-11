package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.InscripcionResponseDTO;
import com.escuela.gestion_api.dto.InscripcionResquestDTO;
import com.escuela.gestion_api.service.InscripcionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/inscripciones")
@RequiredArgsConstructor
public class InscripcionController {
    private final InscripcionService inscripcionService;

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> inscribirAlumno(@Valid @RequestBody InscripcionResquestDTO dto) {
        InscripcionResponseDTO nuevaInscripcion = inscripcionService.inscribirAlumno(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaInscripcion);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.obtenerPorId(id));
    }

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<InscripcionResponseDTO>> obtenerPorAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(inscripcionService.obtenerPorAlumno(alumnoId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelarInscripcion(Long id) {
        inscripcionService.cancelarInscripcion(id);
        return ResponseEntity.noContent().build();
    }

}
