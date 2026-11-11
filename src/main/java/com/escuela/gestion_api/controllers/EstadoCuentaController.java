package com.escuela.gestion_api.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.escuela.gestion_api.dto.EstadoCuentaRequestsDTO;
import com.escuela.gestion_api.dto.EstadoCuentaResponseDTO;
import com.escuela.gestion_api.service.EstadoCuentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/estados-cuenta")
@RequiredArgsConstructor
public class EstadoCuentaController {
    public final EstadoCuentaService estadoCuentaService;

    @GetMapping("/alumno/{alumnoId}")
    public ResponseEntity<List<EstadoCuentaResponseDTO>> obtenerEstadoDeCuentaPorAlumno(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(estadoCuentaService.obtenerEstadoCuentaPorAlumno(alumnoId));
    }

    @PostMapping("/cargo-manual")
    public ResponseEntity<EstadoCuentaResponseDTO> crearCargoPersonalizado(
            @Valid @RequestBody EstadoCuentaRequestsDTO dto) {
        EstadoCuentaResponseDTO nuevoCargo = estadoCuentaService.crearCargoPersonalizado(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCargo);
    }

    @PostMapping("/cargo-inscripcion")
    public ResponseEntity<EstadoCuentaResponseDTO> generarCargoInscripcion(@RequestParam Long alumnoID,
            @RequestParam Long periodoId, @RequestParam(required = false) BigDecimal montoOpcional) {
        EstadoCuentaResponseDTO cargo = estadoCuentaService.generarCargoInscripcion(alumnoID, periodoId, montoOpcional);
        return ResponseEntity.status(HttpStatus.CREATED).body(cargo);
    }

    @GetMapping("/alumno/{alumnoId}/adeudos-vencidos")
    public ResponseEntity<Boolean> tieneAdeudosVencidos(@PathVariable Long alumnoId) {
        return ResponseEntity.ok(estadoCuentaService.tieneAdeudosVencidos(alumnoId));
    }

}
