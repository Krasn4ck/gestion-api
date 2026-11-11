package com.escuela.gestion_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.EstadoCuenta;
import com.escuela.gestion_api.models.EstadoPago;

public interface EstadoCuentaRepository extends JpaRepository<EstadoCuenta, Long> {
    List<EstadoCuenta> findByAlumnoId(long alumnoId);

    List<EstadoCuenta> findByAlumnoIdAndEstadoPago(Long alumnoId, EstadoPago estadoPago);

    Optional<EstadoCuenta> findByReferenciaBancaria(String referenciaBancaria);

    boolean existsByAlumnoIdAndEstadoPago(Long alumnoId, EstadoPago estadoPago);

    List<EstadoCuenta> findByAlumnoIdOrderByFechaEmisionDesc(Long alumnoId);

    List<EstadoCuenta> findByAlumnoIdAndPeriodoLectivoId(Long alumnoId, Long periodoLectivoId);
}
