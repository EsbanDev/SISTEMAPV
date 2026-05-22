package com.sistemapv.repository;

import com.sistemapv.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SolicitudRepository extends JpaRepository <Solicitud, Long> {
    List<Solicitud> findByProducto_Sku(int sku);
}
