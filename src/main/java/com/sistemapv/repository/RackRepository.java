package com.sistemapv.repository;

import com.sistemapv.model.Rack;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack, Long> {
    Optional<Rack> findByCodigoRack(String codigoRack);
}
