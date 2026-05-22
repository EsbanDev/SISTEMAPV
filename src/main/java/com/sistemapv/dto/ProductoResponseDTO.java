package com.sistemapv.dto;

import lombok.Data;

@Data
public class ProductoResponseDTO {
    private Long id;
    private int sku;
    private String nombreProd;
    private int stock;
}
