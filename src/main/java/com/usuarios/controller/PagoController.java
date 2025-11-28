package com.usuarios.controller;

import com.usuarios.model.Pago;
import com.usuarios.service.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired
    private PagoService service;

    @GetMapping
    public List<Pago> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Pago getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Pago create(@RequestBody Pago pago) {
        return service.save(pago);
    }

    @PutMapping("/{id}")
    public Pago update(@PathVariable int id, @RequestBody Pago pago) {
        return service.update(id, pago);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping("/{id}/estado")
    public Pago actualizarEstado(@PathVariable int id, @RequestParam String estado) {
        return service.actualizarEstado(id, estado);
    }
}
