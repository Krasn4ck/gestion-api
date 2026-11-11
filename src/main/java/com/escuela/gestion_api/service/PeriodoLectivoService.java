package com.escuela.gestion_api.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escuela.gestion_api.dto.PeriodoLectivoRequestsDTO;
import com.escuela.gestion_api.dto.PeriodoLectivoResponseDTO;
import com.escuela.gestion_api.models.EstatusPeriodo;
import com.escuela.gestion_api.models.PeriodoLectivo;
import com.escuela.gestion_api.repository.PeriodoLectivoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeriodoLectivoService {
    private final PeriodoLectivoRepository periodoLectivoRepository;

    @Transactional
    public PeriodoLectivoResponseDTO crearPeriodo(PeriodoLectivoRequestsDTO dto) {
        String claveAutoGenerada = generarClavePeriodo(dto.anio(), dto.fechaInicio());

        if (periodoLectivoRepository.existsByClave(claveAutoGenerada)) {
            throw new IllegalArgumentException(
                    "Ya existe un periodo lectivo activo en este momento con Id:" + claveAutoGenerada);
        }
        if (dto.estatus() == EstatusPeriodo.ACTIVO) {
            // aqui usamos el metodo privado de desactivacion de periodos
            desactivarPeriodosActivos();
        }
        PeriodoLectivo periodo = PeriodoLectivo.builder()
                .clave(claveAutoGenerada)
                .nombre(dto.nombre().trim())
                .anio(dto.anio())
                .fechaInicio(dto.fechaInicio())
                .fechaFin(dto.fechaFin())
                .fechaLimiteInscripcion(dto.fechaLimiteInscripcion())
                .estatus(dto.estatus())
                .build();
        return mapToResponseDTO(periodoLectivoRepository.save(periodo));
    }

    public PeriodoLectivoResponseDTO actualizarPeriodo(Long id, PeriodoLectivoRequestsDTO dto) {
        PeriodoLectivo periodoLectivo = periodoLectivoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Periodo Lectivo no encontrado con el Id: " + id));
        periodoLectivo.setNombre(dto.nombre());
        periodoLectivo.setAnio(dto.anio());
        periodoLectivo.setFechaInicio(dto.fechaInicio());
        periodoLectivo.setFechaFin(dto.fechaFin());
        periodoLectivo.setFechaLimiteInscripcion(dto.fechaLimiteInscripcion());
        periodoLectivo.setEstatus(dto.estatus());
        PeriodoLectivo actualizado = periodoLectivoRepository.save(periodoLectivo);
        return mapToResponseDTO(actualizado);
    }

    @Transactional(readOnly = true)
    public List<PeriodoLectivoResponseDTO> obtenerTodos() {
        return periodoLectivoRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PeriodoLectivoResponseDTO obtenerActivo() {
        PeriodoLectivo activo = periodoLectivoRepository.findByEstatus(EstatusPeriodo.ACTIVO)
                .orElseThrow(() -> new RuntimeException("No hay ningu periodo lectivo activo en este momento."));
        return mapToResponseDTO(activo);
    }

    @Transactional
    public PeriodoLectivoResponseDTO cambiarStatus(Long id, EstatusPeriodo nuevoEstatusPeriodo) {
        PeriodoLectivo periodo = periodoLectivoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Periodo lectivo no econtrado con el id: " + id));
        if (nuevoEstatusPeriodo == EstatusPeriodo.ACTIVO) {
            // funcion de desactivacion del periodo
            desactivarPeriodosActivos();
        }
        periodo.setEstatus(nuevoEstatusPeriodo);
        return mapToResponseDTO(periodoLectivoRepository.save(periodo));
    }

    // metodos de uso privado
    private void desactivarPeriodosActivos() {
        periodoLectivoRepository.findByEstatus(EstatusPeriodo.ACTIVO).ifPresent(periodo -> {
            periodo.setEstatus(EstatusPeriodo.FINALIZADO);
            periodoLectivoRepository.save(periodo);
        });
    }

    private String generarClavePeriodo(Integer anio, LocalDate fechaInicio) {
        int semestre = (fechaInicio.getMonthValue() < 7) ? 1 : 2;
        return String.format("%d-%d", anio, semestre);
    }

    private PeriodoLectivoResponseDTO mapToResponseDTO(PeriodoLectivo periodo) {
        return new PeriodoLectivoResponseDTO(
                periodo.getId(),
                periodo.getClave(),
                periodo.getNombre(),
                periodo.getAnio(),
                periodo.getFechaInicio(),
                periodo.getFechaFin(),
                periodo.getFechaLimiteInscripcion(),
                periodo.getEstatus());
    }
}
