package com.escuela.gestion_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escuela.gestion_api.dto.MateriaRequestDTO;
import com.escuela.gestion_api.dto.MateriaResponseDTO;
import com.escuela.gestion_api.models.Materia;
import com.escuela.gestion_api.repository.MateriaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MateriaService {
    private final MateriaRepository materiaRepository;

    @Transactional
    public MateriaResponseDTO crearMateria(MateriaRequestDTO dto) {
        if (materiaRepository.existsByNombreIgnoreCase(dto.nombre())) {
            throw new IllegalArgumentException("Ya existe una materia registrada con ese nombre: " + dto.nombre());
        }
        String claveAutoGenerada = generarClaveUnica(dto.nombre());

        // Creamos y guardamos la entidad
        Materia materia = Materia.builder()
                .clave(claveAutoGenerada)
                .nombre(dto.nombre())
                .creditos(dto.creditos())
                //.carrera(dto.carreraId())
                .build();
        Materia guardada = materiaRepository.save(materia);
        // retornamos y lo mapeamos al DTO
        return mapToDTOMateria(guardada);
    }

    @Transactional(readOnly = true)
    public List<MateriaResponseDTO> obtenerTodas() {
        return materiaRepository.findAll().stream()
                .map(this::mapToDTOMateria)
                .toList();
    }

    @Transactional(readOnly = true)
    public MateriaResponseDTO obtenerPorId(long id) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ese Id: " + id));
        return mapToDTOMateria(materia);
    }

    @Transactional
    public MateriaResponseDTO actualizarMateria(Long id, MateriaRequestDTO dto) {
        Materia materia = materiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada con el id: " + id));
        //materia.setClave(dto.clave());
        materia.setNombre(dto.nombre());
        materia.setCreditos(dto.creditos());

        Materia actualizada = materiaRepository.save(materia);
        return mapToDTOMateria(actualizada);
    }

    public void eliminarMateria(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new IllegalArgumentException("Materia no encontrada con Id: " + id);
        }
        materiaRepository.deleteById(id);
    }

    // metodos privados
    private String generarClaveUnica(String nombre) {
        String prefijo = nombre.length() >= 3
                ? nombre.substring(0, 3).toUpperCase()
                : nombre.toUpperCase();
        String clave;
        do {
            int random = (int) (Math.random() * 9000) + 1000;
            clave = prefijo + "-" + random;
        } while (materiaRepository.existsByClave(clave));

        return clave;
    }

    private MateriaResponseDTO mapToDTOMateria(Materia materia) {
        return new MateriaResponseDTO(
                materia.getId(),
                materia.getClave(),
                materia.getNombre(),
                materia.getCreditos());
    }
}
