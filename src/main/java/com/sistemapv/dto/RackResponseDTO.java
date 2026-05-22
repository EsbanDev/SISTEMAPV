package com.sistemapv.dto;

import lombok.Data;

@Data
public class RackResponseDTO {
    private Long id;
    private String codigoRack;
    private int capacidadMaxima;
    private int capacidadActual;
}
