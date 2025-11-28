package com.usuarios.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.usuarios.model.Usuario;
import com.usuarios.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public List<Usuario> getAllUsuarios() {
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(int id) {
        return repository.findById(id);
    }

    public Usuario saveUsuario(Usuario usuario) {
        return repository.save(usuario);
    }

    public void deleteUsuario(int id) {
        repository.deleteById(id);
    }

    public boolean autenticar(String email, String password) {
        return repository.findByEmail(email)
                .map(u -> u.getPassword().equals(password))
                .orElse(false);
    }

    public Usuario actualizarPerfil(int id, Usuario usuarioActualizado) {
        return repository.findById(id).map(usuario -> {
            usuario.setUsername(usuarioActualizado.getUsername());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setPassword(usuarioActualizado.getPassword());
            usuario.setRole(usuarioActualizado.getRole());
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public class CustomUserDetailsService implements UserDetailsService {

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Usuario usuario = repository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            return new User(
                    usuario.getUsername(),
                    usuario.getPassword(),
                    List.of(new SimpleGrantedAuthority(usuario.getRole().name()))
            );
        }
    }
}