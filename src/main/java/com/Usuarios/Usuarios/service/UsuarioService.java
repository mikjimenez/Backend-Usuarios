package com.Usuarios.Usuarios.service;

import com.Usuarios.Usuarios.model.Usuario;
import com.Usuarios.Usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public boolean autenticar(String email, String contraseña) {
        return repository.findByEmail(email)
                .map(u -> u.getContraseña().equals(contraseña))
                .orElse(false);
    }

    public Usuario actualizarPerfil(int id, Usuario usuarioActualizado) {
        return repository.findById(id).map(usuario -> {
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setContraseña(usuarioActualizado.getContraseña());
            usuario.setTipoUsuario(usuarioActualizado.getTipoUsuario());
            return repository.save(usuario);
        }).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
}