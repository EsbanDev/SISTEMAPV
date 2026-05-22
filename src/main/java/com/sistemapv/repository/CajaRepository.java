package com.sistemapv.repository;

import com.sistemapv.model.Caja;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CajaRepository extends JpaRepository<Caja, Long>{
    Optional<Caja> findByCodigoCaja(String codigoCaja);
    List<Caja> findByRack_CodigoRack(String codigoRack);
    List<Caja> findByUsuarioId(Long usuarioId);
}
