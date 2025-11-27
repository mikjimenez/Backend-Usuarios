package com.usuarios.config;

import com.usuarios.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

	http
		.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource())) // <--- aquí
		.headers(headers -> headers
			.frameOptions(frame -> frame.sameOrigin()) // para H2 console
		)
		.sessionManagement(session ->
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
			// Endpoints públicos
			.requestMatchers(
				"/api/v1/auth/**",
				"/v3/api-docs/**",
				"/swagger-ui.html",
				"/swagger-ui/**",
				"/doc/swagger-ui/**",
				"/h2-console/**"
			).permitAll()
			// Cualquier otro require auth
			.anyRequest().authenticated()
		)
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
	    AuthenticationConfiguration configuration) throws Exception {
	return configuration.getAuthenticationManager();
    }

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();

		// origen del frontend
		config.setAllowedOriginPatterns(List.of("http://localhost:3000"));
		// o, si quieres más abierto en desarrollo:
		// config.setAllowedOriginPatterns(List.of("*"));

		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
		config.setAllowCredentials(true); // si usas cookies o auth con credenciales

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

}
