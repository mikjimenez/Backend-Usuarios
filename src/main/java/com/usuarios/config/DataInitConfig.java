package com.usuarios.config;

import com.usuarios.model.Role;
import com.usuarios.model.Usuario;
import com.usuarios.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitConfig {

    @Bean
    CommandLineRunner initUsers(UsuarioRepository usuarioRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {
            if (usuarioRepository.count() == 0) {
                Usuario admin = Usuario.builder()
                        .username("admin")
                        .email("admin@admin.com")                 // 👈 email NO nulo
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ROLE_ADMIN)
                        .build();
                usuarioRepository.save(admin);

                Usuario user = Usuario.builder()
                        .username("user")
                        .email("user@user.com")                   // 👈 email NO nulo
                        .password(passwordEncoder.encode("user123"))
                        .role(Role.ROLE_USER)
                        .build();
                usuarioRepository.save(user);
            }
        };
    }
}
