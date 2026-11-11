package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.MateriaRequestDTO;
import com.escuela.gestion_api.dto.MateriaResponseDTO;
import com.escuela.gestion_api.service.MateriaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("api/materias")
@RequiredArgsConstructor
public class MateriaController {
    private final MateriaService materiaService;

    @PostMapping
    public ResponseEntity<MateriaResponseDTO> crearMateria(@Valid @RequestBody MateriaRequestDTO dto) {
        MateriaResponseDTO nuevaMateria = materiaService.crearMateria(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaMateria);

    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(materiaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<MateriaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(materiaService.obtenerTodas());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> actualizar(@PathVariable long id,
            @Valid @RequestBody MateriaRequestDTO dto) {
        return ResponseEntity.ok(materiaService.actualizarMateria(id, dto));
    }

    @DeleteMapping
    public ResponseEntity<Void> eliminar(@PathVariable long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

}
