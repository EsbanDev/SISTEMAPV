package com.sistemapv.dto;

import lombok.Data;

@Data
public class ProductoCreateDTO {
    private int sku;
    private String nombreProd;
    private int stock;
}
