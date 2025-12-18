package com.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Respuesta estándar de autenticación.
 *
 * Mantiene el campo "token" (para compatibilidad con el frontend).
 * Agrega info útil (email y roles) para control por rol en UI si lo necesitas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
    private List<String> roles;
}
