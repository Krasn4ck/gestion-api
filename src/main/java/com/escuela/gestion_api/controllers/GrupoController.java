package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.GrupoRequestDTO;
import com.escuela.gestion_api.dto.GrupoResponseDTO;
import com.escuela.gestion_api.service.GrupoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/grupos")
@RequiredArgsConstructor
public class GrupoController {
    private final GrupoService grupoService;

    @PostMapping
    public ResponseEntity<GrupoResponseDTO> crear(@Valid @RequestBody GrupoRequestDTO dto) {
        GrupoResponseDTO nuevoGrupo = grupoService.crearGrupo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoGrupo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(grupoService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<GrupoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(grupoService.obtenerTodos());
    }

    @GetMapping("/periodo/{periodo}")
    public ResponseEntity<List<GrupoResponseDTO>> listarPorPeriodo(String periodo) {
        return ResponseEntity.ok(grupoService.obtenerPorPeriodo(periodo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody GrupoRequestDTO dto) {
        return ResponseEntity.ok(grupoService.actualizarGrupo(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        grupoService.eliminarGrupo(id);
        return ResponseEntity.noContent().build();
    }

}
