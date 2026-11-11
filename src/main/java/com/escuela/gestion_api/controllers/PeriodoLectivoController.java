package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.PeriodoLectivoRequestsDTO;
import com.escuela.gestion_api.dto.PeriodoLectivoResponseDTO;
import com.escuela.gestion_api.models.EstatusPeriodo;
import com.escuela.gestion_api.service.PeriodoLectivoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/periodos-lectivos")
@RequiredArgsConstructor
public class PeriodoLectivoController {
    private final PeriodoLectivoService periodoLectivoService;

    @PostMapping
    public ResponseEntity<PeriodoLectivoResponseDTO> crear(@Valid @RequestBody PeriodoLectivoRequestsDTO dto) {
        PeriodoLectivoResponseDTO nuevoPeriodo = periodoLectivoService.crearPeriodo(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoPeriodo);
    }

    @GetMapping("/activo")
    public ResponseEntity<PeriodoLectivoResponseDTO> obtenerPorActivo() {
        return ResponseEntity.ok(periodoLectivoService.obtenerActivo());
    }

    @GetMapping
    public ResponseEntity<List<PeriodoLectivoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(periodoLectivoService.obtenerTodos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PeriodoLectivoResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody PeriodoLectivoRequestsDTO dto) {
        return ResponseEntity.ok(periodoLectivoService.actualizarPeriodo(id, dto));

    }

    @PatchMapping("/{id}/estatus")
    public ResponseEntity<PeriodoLectivoResponseDTO> finalizar(@PathVariable Long id,
            @RequestParam EstatusPeriodo nuevoPeriodo) {
        return ResponseEntity.ok(periodoLectivoService.cambiarStatus(id, nuevoPeriodo));
    }

}
