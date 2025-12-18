package com.usuarios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.usuarios.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
}
