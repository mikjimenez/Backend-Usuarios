package com.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String descripcion;

    @Column(length = 3, nullable = false)
    private String talla;

    @Column(nullable = false)
    private float precio;

    @Column(nullable = false)
    private int stock;
}   