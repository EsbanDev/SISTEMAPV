package com.sistemapv.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "producto")
@Data
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int sku;
    private String nombreProd;

    private int stock;

    // Relación con recursos
    @OneToMany(mappedBy = "producto")
    private List<Caja> cajas;
}
