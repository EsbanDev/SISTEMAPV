package com.sistemapv.service;

import com.sistemapv.exception.RecursoNoEncontradoException;
import com.sistemapv.dto.MovimientoResponseDTO;
import com.sistemapv.model.Movimiento;
import com.sistemapv.repository.MovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    public List<MovimientoResponseDTO> listarMovimientos() {
        List<Movimiento> movimientos = movimientoRepository.findAll();
        return movimientos.stream().map(this::mapToDTO).toList();
    }

    public MovimientoResponseDTO obtenerPorId(Long id) {
        Movimiento movimiento = movimientoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Movimiento no encontrado"));

        return mapToDTO(movimiento);
    }

    public MovimientoResponseDTO mapToDTO(Movimiento movimiento) {

        MovimientoResponseDTO dto = new MovimientoResponseDTO();

        dto.setId(movimiento.getId());
        dto.setTipoMov(movimiento.getTipoMov());
        dto.setFecha(movimiento.getFecha());

        if (movimiento.getUsuario() != null) {
            dto.setUsuarioId(movimiento.getUsuario().getId());
            dto.setUsuarioUsername(
                    movimiento.getUsuario().getUsername()
            );
        }

        if (movimiento.getCaja() != null) {
            dto.setCajaId(
                    movimiento.getCaja().getId()
            );

            dto.setCodigoCaja(
                    movimiento.getCaja().getCodigoCaja()
            );
        }

        if (movimiento.getRack() != null) {
            dto.setRackId(
                    movimiento.getRack().getId()
            );

            dto.setCodigoRack(
                    movimiento.getRack().getCodigoRack()
            );
        }

        return dto;
    }
}
