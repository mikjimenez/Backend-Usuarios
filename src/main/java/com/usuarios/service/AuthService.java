package com.usuarios.service;

import com.usuarios.dto.AuthRequest;
import com.usuarios.dto.AuthResponse;
import com.usuarios.dto.RegisterRequest;
import com.usuarios.model.Role;
import com.usuarios.model.Usuario;
import com.usuarios.repository.RoleRepository;
import com.usuarios.repository.UsuarioRepository;
import com.usuarios.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        authenticationManager.authenticate(authentication);

        // Cargamos el usuario desde BD para devolver roles/email y generar token consistente
        Usuario usuario = usuarioRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas"));

        UserDetails userDetails = buildUserDetails(usuario);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(usuario.getEmail())
                .roles(usuario.getRoles().stream().map(Role::getName).toList())
                .build();
    }

    public AuthResponse register(RegisterRequest request) {
        String email = request.getEmail() == null ? null : request.getEmail().trim().toLowerCase();
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email es obligatorio");
        }
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password es obligatorio");
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El email ya está registrado");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "ROLE_USER no existe (seed falló)"));

        String username = request.getUsername();
        if (username == null || username.isBlank()) {
            username = email; // fallback
        }

        Usuario nuevo = Usuario.builder()
                .email(email)
                .username(username)
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(roleUser)) // ✅ FORZADO: cualquier registro público es USER
                .build();

        usuarioRepository.save(nuevo);

        UserDetails userDetails = buildUserDetails(nuevo);
        String token = jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .email(nuevo.getEmail())
                .roles(List.of("ROLE_USER"))
                .build();
    }

    private UserDetails buildUserDetails(Usuario usuario) {
        List<String> roleNames = usuario.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList());

        return User.withUsername(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(roleNames.toArray(new String[0]))
                .build();
    }
}
