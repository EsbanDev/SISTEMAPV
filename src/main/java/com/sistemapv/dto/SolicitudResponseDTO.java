package com.sistemapv.dto;

import com.sistemapv.model.Estado;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SolicitudResponseDTO {
    private Long id;
    private int cantidad;
    private LocalDateTime fecha;
    private Estado estado;

    private Long usuarioId;
    private String usuarioUsername;

    private Long productoId;
    private int sku;
    private String nombreProd;
}