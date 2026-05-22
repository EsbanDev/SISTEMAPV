package com.sistemapv.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "movimiento")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) // lo guarda como cadena de texto en la base de datos
    @Column(nullable = false)
    private TipoMov tipoMov;

    private LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "id_caja")
    private Caja caja;

    @ManyToOne
    @JoinColumn(name = "id_rack")
    private Rack rack;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
}

