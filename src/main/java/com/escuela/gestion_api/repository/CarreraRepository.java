package com.escuela.gestion_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.escuela.gestion_api.models.Carrera;
import com.escuela.gestion_api.models.NivelEducativo;

public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Optional<Carrera> findByClave(String clave);

    List<Carrera> findByNivelEducativo(NivelEducativo nivelEducativo);

    Boolean existsByClave(String clave);
}
