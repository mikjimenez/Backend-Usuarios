package com.Usuarios.Usuarios.controller;

import com.Usuarios.Usuarios.model.Usuario;
import com.Usuarios.Usuarios.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario> getAll() {
        return service.getAllUsuarios();
    }

    @GetMapping("/{id}")
    public Optional<Usuario> getById(@PathVariable int id) {
        return service.getUsuarioById(id);
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return service.saveUsuario(usuario);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable int id, @RequestBody Usuario usuario) {
        return service.actualizarPerfil(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteUsuario(id);
    }

    @PostMapping("/login")
    public boolean autenticar(@RequestParam String email, @RequestParam String contraseña) {
        return service.autenticar(email, contraseña);
    }
}