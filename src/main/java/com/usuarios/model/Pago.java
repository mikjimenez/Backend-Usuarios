package com.usuarios.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int pedidoId;

    @Column(length = 15, nullable = false)
    private String metodo; // E.g., "Tarjeta", "Transferencia", "Efectivo"

    @Column(nullable = false)
    private float monto;

    @Column(length = 15, nullable = false)
    private String estado; // E.g., "Pendiente", "Completado", "Fallido"

    @Column(nullable = false)
    private LocalDateTime fecha;
}