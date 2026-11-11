package com.escuela.gestion_api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.escuela.gestion_api.models.EstadoPago;

public record EstadoCuentaResponseDTO(
        Long id,
        long alumnoId,
        String nombreAlumno,
        String clavePeriodo,
        String concepto,
        BigDecimal monto,
        BigDecimal montoPagado,
        BigDecimal saldoPendiente,
        LocalDate fechaEmision,
        LocalDate fechaVencimiento,
        String referenciaBancaria,
        EstadoPago estado

) {

}
