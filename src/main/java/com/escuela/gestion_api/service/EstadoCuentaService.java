package com.escuela.gestion_api.service;

import com.escuela.gestion_api.dto.EstadoCuentaRequestsDTO;
import com.escuela.gestion_api.dto.EstadoCuentaResponseDTO;
import com.escuela.gestion_api.models.Alumno;
import com.escuela.gestion_api.models.EstadoCuenta;
import com.escuela.gestion_api.models.EstadoPago;
import com.escuela.gestion_api.models.NivelEducativo;
import com.escuela.gestion_api.models.PeriodoLectivo;
import com.escuela.gestion_api.repository.AlumnoRepository;
import com.escuela.gestion_api.repository.EstadoCuentaRepository;
import com.escuela.gestion_api.repository.PeriodoLectivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EstadoCuentaService {

    private static final BigDecimal TARIFA_BACHILLERATO_DEFAULT = new BigDecimal("1200.00");
    private static final BigDecimal TARIFA_UNIVERSIDAD_DEFAULT = new BigDecimal("1800.00");

    private final EstadoCuentaRepository estadoCuentaRepository;
    private final AlumnoRepository alumnoRepository;
    private final PeriodoLectivoRepository periodoLectivoRepository;

    @Transactional
    public EstadoCuenta generarCargoInscripcion(Alumno alumno, PeriodoLectivo periodo, BigDecimal montoOpcional) {
        BigDecimal montoFinal = (montoOpcional != null && montoOpcional.compareTo(BigDecimal.ZERO) > 0)
                ? montoOpcional
                : obtenerPrecioInscripcionPorNivel(alumno.getNivelEducativo());

        EstadoCuenta cargoInscripcion = EstadoCuenta.builder()
                .alumno(alumno)
                .periodoLectivo(periodo)
                .concepto("Cuota de Inscripción - " + alumno.getNivelEducativo() + " (" + periodo.getClave() + ")")
                .monto(montoFinal)
                .montoPagado(BigDecimal.ZERO)
                .fechaEmision(LocalDate.now())
                .fechaVencimiento(LocalDate.now().plusDays(10))
                .referenciaBancaria(alumno.getReferenciaBancaria())
                .estadoPago(EstadoPago.PENDIENTE)
                .build();

        return estadoCuentaRepository.save(cargoInscripcion);
    }

    @Transactional
    public EstadoCuentaResponseDTO generarCargoInscripcion(Long alumnoId, Long periodoId, BigDecimal montoOpcional) {
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado con ID: " + alumnoId));

        PeriodoLectivo periodo = periodoLectivoRepository.findById(periodoId)
                .orElseThrow(() -> new IllegalArgumentException("Periodo lectivo no encontrado con ID: " + periodoId));

        EstadoCuenta cargo = generarCargoInscripcion(alumno, periodo, montoOpcional);
        return mapToDTO(cargo);
    }

    /**
     * Permite registrar cargos manuales o personalizados (ej. constancias,
     * exámenes).
     */
    @Transactional
    public EstadoCuentaResponseDTO crearCargoPersonalizado(EstadoCuentaRequestsDTO dto) {
        Alumno alumno = alumnoRepository.findById(dto.alumnoId())
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado con ID: " + dto.alumnoId()));

        PeriodoLectivo periodo = periodoLectivoRepository.findById(dto.peridoLectivoId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Periodo no encontrado con ID: " + dto.peridoLectivoId()));

        EstadoCuenta nuevoCargo = EstadoCuenta.builder()
                .alumno(alumno)
                .periodoLectivo(periodo)
                .concepto(dto.concepto())
                .monto(dto.monto())
                .montoPagado(BigDecimal.ZERO)
                .fechaEmision(LocalDate.now())
                .fechaVencimiento(dto.fechaVencimiento())
                .referenciaBancaria(alumno.getReferenciaBancaria())
                .estadoPago(EstadoPago.PENDIENTE)
                .build();

        return mapToDTO(estadoCuentaRepository.save(nuevoCargo));
    }

    @Transactional(readOnly = true)
    public List<EstadoCuentaResponseDTO> obtenerEstadoCuentaPorAlumno(Long alumnoId) {
        return estadoCuentaRepository.findByAlumnoIdOrderByFechaEmisionDesc(alumnoId)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public boolean tieneAdeudosVencidos(Long alumnoId) {
        return estadoCuentaRepository.existsByAlumnoIdAndEstadoPago(alumnoId, EstadoPago.VENCIDO);
    }

    public BigDecimal obtenerPrecioInscripcionPorNivel(NivelEducativo nivel) {
        if (nivel == null) {
            throw new IllegalArgumentException("El alumno no tiene un Nivel Educativo asignado.");
        }

        return switch (nivel) {
            case BACHILLERATO -> TARIFA_BACHILLERATO_DEFAULT;
            case UNIVERSIDAD -> TARIFA_UNIVERSIDAD_DEFAULT;
        };
    }

    public EstadoCuentaResponseDTO mapToDTO(EstadoCuenta e) {
        BigDecimal saldoPendiente = e.getMonto().subtract(e.getMontoPagado());
        String nombreCompleto = e.getAlumno().getNombre() + " " + e.getAlumno().getApellidoPaterno() + " "
                + e.getAlumno().getApellidoMaterno();

        return new EstadoCuentaResponseDTO(
                e.getId(),
                e.getAlumno().getId(),
                nombreCompleto,
                e.getPeriodoLectivo().getClave(),
                e.getConcepto(),
                e.getMonto(),
                e.getMontoPagado(),
                saldoPendiente,
                e.getFechaEmision(),
                e.getFechaVencimiento(),
                e.getReferenciaBancaria(),
                e.getEstadoPago());
    }
}