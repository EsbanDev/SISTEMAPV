package com.sistemapv.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Entity
@Table(name = "caja")
@Data
public class Caja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String codigoCaja;

    private int cantidadProductos; // "archivo" o "enlace"

    private LocalDateTime fechaIngreso;

    @ManyToOne
    @JoinColumn(name = "id_producto")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "id_rack")
    private Rack rack;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "caja")
    private List<Movimiento> movimientos;
}
