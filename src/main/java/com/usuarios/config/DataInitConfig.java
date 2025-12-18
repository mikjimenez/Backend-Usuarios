package com.usuarios.config;

import com.usuarios.model.Producto;
import com.usuarios.model.Role;
import com.usuarios.model.Usuario;
import com.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import com.usuarios.repository.ProductoRepository;
import com.usuarios.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UsuarioRepository UsuarioRepository;
    private final ProductoRepository ProductoRepository;

    @Override
    public void run(String... args) {

        // ===== ROLES =====
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name("ROLE_USER").build()
                ));

        Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name("ROLE_ADMIN").build()
                ));

        // ===== USUARIO ADMIN =====
        if (!UsuarioRepository.existsByEmail("admin@moda.com")) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Usuario admin = Usuario.builder()
                    .email("admin@moda.com")
                    .username("Administrador Moda Urbana")
                    .password(passwordEncoder.encode("admin123"))
                    .enabled(true)
                    .roles(Set.of(roleUser, roleAdmin))
                    .build();

            UsuarioRepository.save(admin);
            System.out.println("✅ Usuario admin creado: admin@moda.com / admin123");
        }

        // ===== PRODUCTOS DEMO =====
        if (ProductoRepository.count() == 0) {
            List<Producto> productos = List.of(
                    // 1
                    Producto.builder()
                            .nombre("Polera Heavyweight Tee 'Skysurfer'")
                            .descripcion("Polera heavyweight oversized con diseño Skysurfer. Confección premium en algodón 100% para máxima comodidad y durabilidad.")
                            .material("Algodón 100%")
                            .talla("M")
                            .precio(28990)
                            .stock(20)
                            .categoria("Poleras")
                            .imageUrl("https://www.stodak.com/cdn/shop/files/DROPANIVERSARIO-04.png?v=1757284633&width=1050")
                            .build()
            );

            ProductoRepository.saveAll(productos);
            System.out.println("Productos iniciales creados: " + productos.size());
        }
    }
}