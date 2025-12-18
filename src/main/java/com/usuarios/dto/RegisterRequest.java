package com.usuarios.dto;

import lombok.Data;

/**
 * Request para registro público.
 *
 * El backend siempre asigna ROLE_USER al registrar.
 */
@Data
public class RegisterRequest {
    /** Nombre a mostrar (puede ser null). */
    private String username;

    /** Email único (se usa también para login). */
    private String email;

    /** Password en texto plano (se encripta antes de guardar). */
    private String password;
}
