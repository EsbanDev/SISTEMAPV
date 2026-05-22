package com.sistemapv.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CajaResponseDTO {
    private Long id;
    private String codigoCaja;
    private int cantidadProductos;
    private LocalDateTime fechaIngreso;

    private Long usuarioId;
    private String usuarioUsername;

    private Long rackId;
    private String codigoRack;

    private Long productoId;
    private int sku;
    private String nombreProd;
}
