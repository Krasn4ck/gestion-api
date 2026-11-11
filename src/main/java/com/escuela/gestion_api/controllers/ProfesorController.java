package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.ProfesorRequestDTO;
import com.escuela.gestion_api.dto.ProfesorResponseDTO;
import com.escuela.gestion_api.service.ProfesorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/profesores")
@RequiredArgsConstructor
public class ProfesorController {
    private final ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<ProfesorResponseDTO> crear(@Valid @RequestBody ProfesorRequestDTO dto) {
        ProfesorResponseDTO nuevoProfesor = profesorService.crearProfesor(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProfesor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ProfesorResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(profesorService.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfesorResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody ProfesorRequestDTO dto) {
        return ResponseEntity.ok(profesorService.actualizarProfesor(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
        return ResponseEntity.noContent().build();
    }
}
