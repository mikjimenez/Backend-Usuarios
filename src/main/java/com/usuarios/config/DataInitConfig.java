package com.usuarios.config;

import com.usuarios.model.Producto;
import com.usuarios.model.Role;
import com.usuarios.model.Usuario;
import com.usuarios.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

import com.usuarios.repository.ProductoRepository;
import com.usuarios.repository.RoleRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final PasswordEncoder passwordEncoder;

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
        String email = "modaurbana@spa.com";
        String username = "Administrador ModaUrbana";

        boolean exists = usuarioRepository.existsByEmail(email) || usuarioRepository.existsByUsername(username);

        if (!exists) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            Usuario admin = Usuario.builder()
                    .email(email)
                    .username(username)
                    .password(passwordEncoder.encode("admin12345"))
                    .enabled(true)
                    .roles(Set.of(roleUser, roleAdmin))
                    .build();

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario admin creado: modaurbana@spa.com / admin12345");
        } else {
            System.out.println("ℹ️ Usuario admin ya existe, no se vuelve a insertar.");
        }

        // ===== PRODUCTOS DEMO =====
        if (productoRepository.count() == 0) {
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
                            .build(),
                        // 2
                        Producto.builder()
                                .nombre("Polerón Heavyweight 'Skysurfer' Fullzip")
                                .descripcion("Polerón heavyweight con cierre completo y capucha. Diseño exclusivo Skysurfer con terminaciones de calidad.")
                                .material("Algodón 80% / Poliéster 20%")
                                .talla("L")
                                .precio(64990)
                                .stock(12)
                                .categoria("Hoodies")
                                .imageUrl("https://www.stodak.com/cdn/shop/files/DROPANIVERSARIO-03.png?v=1757284679&width=1200")
                                .build(),
                        // 3
                        Producto.builder()
                                .nombre("Poleron Heavyweight 'Aniversario 5'")
                                .descripcion("Edición especial Aniversario 5. Polerón heavyweight fullzip con diseño conmemorativo.")
                                .material("Algodón 80% / Poliéster 20%")
                                .talla("L")
                                .precio(64990)
                                .stock(10)
                                .categoria("Hoodies")
                                .imageUrl("https://www.stodak.com/cdn/shop/files/DROPANIVERSARIO-10.png?v=1757284664&width=1200")
                                .build());
            productoRepository.saveAll(productos);
            System.out.println("Productos iniciales creados: " + productos.size());
        }
    }
}