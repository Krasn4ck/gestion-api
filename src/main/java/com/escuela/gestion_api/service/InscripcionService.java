package com.escuela.gestion_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.escuela.gestion_api.dto.InscripcionResponseDTO;
import com.escuela.gestion_api.dto.InscripcionResquestDTO;
import com.escuela.gestion_api.models.Alumno;
import com.escuela.gestion_api.models.EstadoInscripcion;
import com.escuela.gestion_api.models.Grupo;
import com.escuela.gestion_api.models.Inscripcion;
import com.escuela.gestion_api.models.PeriodoLectivo;
import com.escuela.gestion_api.repository.AlumnoRepository;
import com.escuela.gestion_api.repository.GrupoRepository;
import com.escuela.gestion_api.repository.InscripcionRepository;
import com.escuela.gestion_api.repository.PeriodoLectivoRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InscripcionService {
        private final InscripcionRepository inscripcionRepository;
        private final AlumnoRepository alumnoRepository;
        private final GrupoRepository grupoRepository;
        private final PeriodoLectivoRepository periodoLectivoRepository;
        private final EstadoCuentaService estadoCuentaService;

        @Transactional
        public InscripcionResponseDTO inscribirAlumno(InscripcionResquestDTO dto) {
                Alumno alumno = alumnoRepository.findById(dto.alumnoId())
                                .orElseThrow(() -> new RuntimeException(
                                                "No se encuentra el alumno con ese Id:" + dto.alumnoId()));
                Grupo grupo = grupoRepository.findById(dto.grupoId())
                                .orElseThrow(() -> new RuntimeException(
                                                "No existe un Grupo con el Id: " + dto.grupoId()));
                PeriodoLectivo periodoLectivo = periodoLectivoRepository.findById(dto.periodoLectivoId())
                                .orElseThrow(() -> new RuntimeException(
                                                "No exsite el periodo lectivo con ese Id:" + dto.periodoLectivoId()));
                if (estadoCuentaService.tieneAdeudosVencidos(alumno.getId())) {
                        throw new IllegalArgumentException(
                                        "El alumno presenta adeudos vencidos, no se puede procesar la peticion.");
                }
                boolean yaInscrito = inscripcionRepository.existsByAlumnoIdAndGrupoPeriodoLectivoId(alumno.getId(),
                                periodoLectivo.getId());
                if (yaInscrito) {
                        throw new IllegalArgumentException(
                                        "El alumno ya se encuentra inscrito en el periodo seleccionado");
                }
                Inscripcion inscripcion = Inscripcion.builder()
                                .alumno(alumno)
                                .grupo(grupo)
                                .periodoLectivo(periodoLectivo)
                                .estadoInscripcion(EstadoInscripcion.INSCRITO)
                                .build();
                Inscripcion guardado = inscripcionRepository.save(inscripcion);
                estadoCuentaService.generarCargoInscripcion(alumno, periodoLectivo, dto.montoInscripcion());

                return mapToDTOInscripcionResponseDTO(guardado);

        }

        public InscripcionResponseDTO obtenerPorId(Long id) {
                Inscripcion inscripcion = inscripcionRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "No se encontro una inscripcion con ese Id: " + id));
                return mapToDTOInscripcionResponseDTO(inscripcion);

        }

        @Transactional(readOnly = true)
        public List<InscripcionResponseDTO> obtenerPorAlumno(long alumnoId) {
                return inscripcionRepository.findByAlumnoId(alumnoId).stream()
                                .map(this::mapToDTOInscripcionResponseDTO)
                                .toList();
        }

        @Transactional(readOnly = true)
        public List<InscripcionResponseDTO> obtenerInscripcionesPorGrupo(Long grupoId) {
                return inscripcionRepository.findByGrupoId(grupoId)
                                .stream()
                                .map(this::mapToDTOInscripcionResponseDTO)
                                .toList();
        }

        public void cancelarInscripcion(Long id) {
                Inscripcion inscripcion = inscripcionRepository.findById(id)
                                .orElseThrow(() -> new IllegalArgumentException(
                                                "Inscripcion no encontrada con el Id: " + id));
                inscripcionRepository.delete(inscripcion);
        }

        // metodos de privados
        private InscripcionResponseDTO mapToDTOInscripcionResponseDTO(Inscripcion inscripcion) {
                String nombreCompleto = inscripcion.getAlumno().getNombre() + " "
                                + inscripcion.getAlumno().getApellidoPaterno()
                                + " " + inscripcion.getAlumno().getApellidoMaterno();
                String claveGrupo = inscripcion.getGrupo().getClaveGrupo();
                String nombreMateria = inscripcion.getGrupo().getMateria() != null
                                ? inscripcion.getGrupo().getMateria().getNombre()
                                : null;
                Double calificacionFinal = null;
                return new InscripcionResponseDTO(
                                inscripcion.getId(),
                                inscripcion.getAlumno().getId(),
                                nombreCompleto,
                                inscripcion.getAlumno().getMatricula(),
                                inscripcion.getGrupo().getId(),
                                claveGrupo,
                                nombreMateria,
                                calificacionFinal,
                                inscripcion.getEstadoInscripcion(),
                                inscripcion.getFechaInscripcion(),
                                inscripcion.getPeriodoLectivo().getClave()

                );
        }
}
