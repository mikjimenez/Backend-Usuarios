package com.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int clienteId;

    @ElementCollection
    @Column(nullable = false)
    private List<Integer> productos; // IDs de productos

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private float total;
}
