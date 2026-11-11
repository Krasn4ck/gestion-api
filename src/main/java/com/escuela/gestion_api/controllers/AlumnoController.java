package com.escuela.gestion_api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.AlumnoResponseDTO;
import com.escuela.gestion_api.dto.AlumnoRequestsDTO;
import com.escuela.gestion_api.service.AlumnoService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService alumnoService;

    @GetMapping
    public ResponseEntity<List<AlumnoResponseDTO>> listarAlumnos() {
        return ResponseEntity.ok(alumnoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlumnoResponseDTO> obtenerAlumnoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alumnoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlumnoResponseDTO> crearAlumno(@Valid @RequestBody AlumnoRequestsDTO dto) {
        AlumnoResponseDTO alumnoCreado = alumnoService.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(alumnoCreado);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AlumnoResponseDTO> actualizar(@PathVariable Long id,
            @Valid @RequestBody AlumnoRequestsDTO dto) {
        return ResponseEntity.ok(alumnoService.actualizarAlumno(id, dto));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
        return ResponseEntity.noContent().build();
    }

}
