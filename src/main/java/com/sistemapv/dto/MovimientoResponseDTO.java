package com.sistemapv.dto;

import com.sistemapv.model.TipoMov;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovimientoResponseDTO {
    private Long id;
    private TipoMov tipoMov;
    private LocalDateTime fecha;

    private Long usuarioId;
    private String usuarioUsername;

    private Long cajaId;
    private String codigoCaja;

    private Long rackId;
    private String codigoRack;
}
