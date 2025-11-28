package com.usuarios.controller;

import com.usuarios.dto.AuthRequest;
import com.usuarios.dto.AuthResponse;
import com.usuarios.security.JwtService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Endpoints para autenticación con JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        authenticationManager.authenticate(authentication);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(request.getUsername());

        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}