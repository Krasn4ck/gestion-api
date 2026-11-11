package com.escuela.gestion_api.service;

import java.time.Year;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escuela.gestion_api.dto.ProfesorRequestDTO;
import com.escuela.gestion_api.dto.ProfesorResponseDTO;
import com.escuela.gestion_api.models.Profesor;
import com.escuela.gestion_api.repository.ProfesorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfesorService {
    private final ProfesorRepository profesorRepository;

    // metodos publicos de la API
    @Transactional
    public ProfesorResponseDTO crearProfesor(ProfesorRequestDTO dto) {
        String numeroEmpleadoGenerado = generarNumeroEmpleadoUnico();

        Profesor profesor = Profesor.builder()
                .numeroEmpleado(numeroEmpleadoGenerado)
                .nombre(dto.nombre())
                .apellidoPaterno(dto.apellidoPaterno())
                .apellidoMaterno(dto.apellidoMaterno())
                .Cubiculo(dto.cubiculo())
                .build();
        Profesor guardado = profesorRepository.save(profesor);
        return mapToResponseDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<ProfesorResponseDTO> obtenerTodos() {
        return profesorRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProfesorResponseDTO obtenerPorId(Long id) {
        Profesor profesor = profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con el Id: " + id));
        return mapToResponseDTO(profesor);
    }

    @Transactional
    public ProfesorResponseDTO actualizarProfesor(Long id, ProfesorRequestDTO dto) {
        Profesor profesorExistente = profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con el Id: " + id));
        profesorExistente.setNombre(dto.nombre());
        profesorExistente.setApellidoPaterno(dto.apellidoPaterno());
        profesorExistente.setApellidoMaterno(dto.apellidoMaterno());
        profesorExistente.setCubiculo(dto.cubiculo());

        Profesor actualizado = profesorRepository.save(profesorExistente);
        return mapToResponseDTO(actualizado);
    }

    @Transactional
    public void eliminarProfesor(Long id) {
        if (!profesorRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar, profesor no encontrado en el sistema con Id:." + id);
        }
        profesorRepository.deleteById(id);
    }

    // metodos privados
    private String generarNumeroEmpleadoUnico() {
        int anioActual = Year.now().getValue();
        String prefijoAnio = "PROF-" + anioActual + "-";

        Long totalRegistros = profesorRepository.count();
        Long consecutivo = totalRegistros + 1;
        String numeroEmpleado;
        do {
            numeroEmpleado = String.format("%s%04d", prefijoAnio, consecutivo);
            consecutivo++;
        } while (profesorRepository.existsByNumeroEmpleado(numeroEmpleado));
        return numeroEmpleado;
    }

    private ProfesorResponseDTO mapToResponseDTO(Profesor profesor) {
        return new ProfesorResponseDTO(
                profesor.getId(),
                profesor.getNumeroEmpleado(),
                profesor.getNombre(),
                profesor.getApellidoPaterno(),
                profesor.getApellidoMaterno(),
                profesor.getCubiculo());
    }
}
