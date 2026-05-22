package com.sistemapv.repository;

import com.sistemapv.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
