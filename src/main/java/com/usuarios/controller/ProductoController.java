package com.usuarios.controller;

import com.usuarios.model.Producto;
import com.usuarios.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public List<Producto> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Producto getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public Producto create(@RequestBody Producto producto) {
        return service.save(producto);
    }

    @PutMapping("/{id}")
    public Producto update(@PathVariable int id, @RequestBody Producto producto) {
        return service.update(id, producto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping("/{id}/stock")
    public Producto actualizarStock(@PathVariable int id, @RequestParam int nuevoStock) {
        return service.actualizarStock(id, nuevoStock);
    }

    @PutMapping("/{id}/descuento")
    public Producto aplicarDescuento(@PathVariable int id, @RequestParam float porcentaje) {
        return service.aplicarDescuento(id, porcentaje);
    }
}