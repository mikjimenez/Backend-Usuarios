package com.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String material;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String talla;

    @Column(nullable = false)
    private float precio;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private String categoria;

    @Column(nullable = true)
    private String imageUrl;
}
   