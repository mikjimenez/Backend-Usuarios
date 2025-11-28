package com.usuarios.controller;

import com.usuarios.model.Pedido;
import com.usuarios.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    public List<Pedido> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pedido getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Pedido create(@RequestBody Pedido pedido) {
        return service.save(pedido);
    }

    @PutMapping("/{id}")
    public Pedido update(@PathVariable int id, @RequestBody Pedido pedido) {
        return service.update(id, pedido);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping("/{id}/estado")
    public Pedido actualizarEstado(@PathVariable int id, @RequestParam String estado) {
        return service.actualizarEstado(id, estado);
    }
}
