package com.escuela.gestion_api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.escuela.gestion_api.dto.GrupoRequestDTO;
import com.escuela.gestion_api.dto.GrupoResponseDTO;
import com.escuela.gestion_api.models.Grupo;
import com.escuela.gestion_api.models.Materia;
import com.escuela.gestion_api.models.PeriodoLectivo;
import com.escuela.gestion_api.models.Profesor;
import com.escuela.gestion_api.repository.GrupoRepository;
import com.escuela.gestion_api.repository.MateriaRepository;
import com.escuela.gestion_api.repository.PeriodoLectivoRepository;
import com.escuela.gestion_api.repository.ProfesorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GrupoService {
        private final GrupoRepository grupoRepository;
        private final MateriaRepository materiaRepository;
        private final ProfesorRepository profesorRepository;
        private final PeriodoLectivoRepository periodoLectivoRepository;

        @Transactional
        public GrupoResponseDTO crearGrupo(GrupoRequestDTO dto) {
                Materia materia = materiaRepository.findById(dto.materiaId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Materia no encontrada con Id: " + dto.materiaId()));

                Profesor profesor = profesorRepository.findById(dto.profesorId())
                                .orElseThrow(() -> new RuntimeException("El profesor con Id" + dto.profesorId()));

                PeriodoLectivo periodo = periodoLectivoRepository.findById(dto.periodoLectivoId())
                                .orElseThrow(() -> new RuntimeException(
                                                "El periodo lectivo con Id: " + dto.periodoLectivoId()
                                                                + " No existe."));

                String claveGrupo = generarClaveGrupoUnica(materia, periodo.getClave());

                Grupo grupo = Grupo.builder()
                                .claveGrupo(claveGrupo)
                                .periodoLectivo(periodo)
                                .aula(dto.aula())
                                .cupoMaximo(dto.cupoMaximo())
                                .cupoDisponible(dto.cupoMaximo())
                                .materia(materia)
                                .profesor(profesor)
                                .build();
                Grupo guardado = grupoRepository.save(grupo);
                return mapToResponseDTO(guardado);
        }

        @Transactional(readOnly = true)
        public List<GrupoResponseDTO> obtenerTodos() {
                return grupoRepository.findAll().stream()
                                .map(this::mapToResponseDTO)
                                .toList();
        }

        @Transactional(readOnly = true)
        public GrupoResponseDTO obtenerPorId(Long id) {
                Grupo grupo = grupoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con el Id: " + id));
                return mapToResponseDTO(grupo);
        }

        @Transactional(readOnly = true)
        public List<GrupoResponseDTO> obtenerPorPeriodo(String periodo) {
                return grupoRepository.findByPeriodoLectivo(periodo).stream()
                                .map(this::mapToResponseDTO)
                                .toList();

        }

        @Transactional
        public GrupoResponseDTO actualizarGrupo(Long id, GrupoRequestDTO dto) {
                Grupo grupoExistente = grupoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con el Id:" + id));
                Profesor nuevoProfesor = profesorRepository.findById(dto.profesorId())
                                .orElseThrow(() -> new RuntimeException(
                                                "Profesor no encontrado con el Id: " + dto.profesorId()));
                // necesitamos ajustar cupos en dado caso que se necesite
                int inscritosActuales = grupoExistente.getCupoMaximo() - grupoExistente.getCupoDisponible();
                if (dto.cupoMaximo() < inscritosActuales) {
                        throw new IllegalArgumentException("El cupo maximo (" + dto.cupoMaximo() +
                                        "no puede ser menor a los alumnos ya inscritos (" + inscritosActuales + ")");
                }
                grupoExistente.setAula(dto.aula());
                grupoExistente.setProfesor(nuevoProfesor);
                grupoExistente.setCupoMaximo(dto.cupoMaximo());
                grupoExistente.setCupoDisponible(dto.cupoMaximo() - inscritosActuales);

                Grupo grupoActualizado = grupoRepository.save(grupoExistente);
                return mapToResponseDTO(grupoActualizado);
        }

        public void eliminarGrupo(Long id) {
                Grupo grupo = grupoRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Grupo no encontrado con el Id: " + id));
                int inscritos = grupo.getCupoMaximo() - grupo.getCupoDisponible();
                if (inscritos > 0) {
                        throw new IllegalStateException("No se pude eliminar un grupo que ya tiene alumnos.");
                }
                grupoRepository.deleteById(id);
        }

        // metodos privados
        private String generarClaveGrupoUnica(Materia materia, String periodo) {
                long consecutivo = grupoRepository.countByMateriaIdAndPeriodoLectivoClave(materia.getId(), periodo) + 1;
                String prefijoMateria = materia.getClave().toUpperCase();
                return String.format("GRP-%s-%03d", prefijoMateria, consecutivo);
        }

        private GrupoResponseDTO mapToResponseDTO(Grupo grupo) {
                return new GrupoResponseDTO(
                                grupo.getId(),
                                grupo.getClaveGrupo(),
                                grupo.getAula(),
                                grupo.getCupoMaximo(),
                                grupo.getCupoDisponible(),
                                grupo.getMateria().getNombre(),
                                grupo.getMateria().getCarrera() != null ? grupo.getMateria().getCarrera().getNombre()
                                                : "GENERAL",
                                grupo.getProfesor().getNombre() + " " + grupo.getProfesor().getApellidoMaterno(),
                                grupo.getPeriodoLectivo().getClave(),
                                grupo.getPeriodoLectivo().getNombre());
        }
}
