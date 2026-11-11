package com.escuela.gestion_api.models;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "estados_cuenta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EstadoCuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "periodo_lectivo_id", nullable = false)
    private PeriodoLectivo periodoLectivo;
    @Column(nullable = false, length = 150)
    private String concepto;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;
    @Column(nullable = false)
    private LocalDate fechaEmision;
    @Column(nullable = false)
    private LocalDate fechaVencimiento;
    @Column(length = 50, unique = true)
    private String referenciaBancaria;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPago estadoPago;

}
