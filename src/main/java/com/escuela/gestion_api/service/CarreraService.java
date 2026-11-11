package com.escuela.gestion_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escuela.gestion_api.dto.CarreraRequestsDTO;
import com.escuela.gestion_api.dto.CarreraResponseDTO;
import com.escuela.gestion_api.models.Carrera;
import com.escuela.gestion_api.models.NivelEducativo;
import com.escuela.gestion_api.repository.CarreraRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarreraService {
    private final CarreraRepository carreraRepository;

    @Transactional
    public CarreraResponseDTO crearCarrera(CarreraRequestsDTO dto) {
        String claveFormateada = dto.clave().trim().toUpperCase();
        if (carreraRepository.existsByClave(claveFormateada)) {
            throw new IllegalArgumentException(
                    "Ya existe una carrera o programa registrado con la clave: " + claveFormateada);
        }
        Carrera carrera = Carrera.builder()
                .clave(claveFormateada)
                .nombre(dto.nombre())
                .nivelEducativo(dto.nivelEducativo())
                .build();
        Carrera guardada = carreraRepository.save(carrera);
        return mapToResponseDTO(guardada);
    }

    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> obtenerTodas() {
        return carreraRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CarreraResponseDTO obtenerPorId(Long id) {
        Carrera carrera = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con el id: " + id));
        return mapToResponseDTO(carrera);
    }

    @Transactional(readOnly = true)
    public List<CarreraResponseDTO> obtenerPorNivelEducativo(NivelEducativo nivelEducativo) {
        return carreraRepository.findByNivelEducativo(nivelEducativo).stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Transactional
    public CarreraResponseDTO actualizarCarrera(Long id, CarreraRequestsDTO dto) {
        Carrera carreraExistente = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con el Id:" + id));

        String nuevaClave = dto.clave().trim().toUpperCase();

        if (!carreraExistente.getClave().equalsIgnoreCase(nuevaClave) && carreraRepository.existsByClave(nuevaClave)) {
            throw new IllegalArgumentException("La clave nueva: " + nuevaClave + "ya esta en uso por otra carrera");
        }
        carreraExistente.setClave(nuevaClave);
        carreraExistente.setNombre(dto.nombre().trim());
        carreraExistente.setNivelEducativo(dto.nivelEducativo());

        Carrera actualizda = carreraRepository.save(carreraExistente);
        return mapToResponseDTO(actualizda);
    }

    @Transactional
    public void eliminarCarrera(Long id) {
        if (!carreraRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar carrera no encontrada con el Id: " + id);
        }
        carreraRepository.deleteById(id);
    }

    // metodos privados
    private CarreraResponseDTO mapToResponseDTO(Carrera carrera) {
        return new CarreraResponseDTO(
                carrera.getId(),
                carrera.getClave(),
                carrera.getNombre(),
                carrera.getNivelEducativo());
    }
}