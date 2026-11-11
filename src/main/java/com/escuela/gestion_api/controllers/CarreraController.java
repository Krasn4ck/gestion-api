package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.CarreraRequestsDTO;
import com.escuela.gestion_api.dto.CarreraResponseDTO;
import com.escuela.gestion_api.service.CarreraService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/carreras")
@RequiredArgsConstructor
public class CarreraController {
    private final CarreraService carreraService;

    @PostMapping
    public ResponseEntity<CarreraResponseDTO> crear(@Valid @RequestBody CarreraRequestsDTO dto) {
        CarreraResponseDTO nuevaCarrera = carreraService.crearCarrera(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCarrera);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(carreraService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<CarreraResponseDTO>> listarTodos() {
        return ResponseEntity.ok(carreraService.obtenerTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody CarreraRequestsDTO dto) {
        return ResponseEntity.ok(carreraService.actualizarCarrera(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return ResponseEntity.noContent().build();
    }
}
