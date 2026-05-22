package com.sistemapv.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.Data;

@Entity
@Table(name = "rack")
@Data
public class Rack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoRack;

    private int capacidadMaxima;

    private int capacidadActual;

    // Relación con recursos
    @OneToMany(mappedBy = "rack")
    private List<Caja> cajas;

    @OneToMany(mappedBy = "rack")
    private List<Movimiento> movimientos;
}

