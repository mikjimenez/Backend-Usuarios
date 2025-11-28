package com.usuarios.service;

import com.usuarios.model.Producto;
import com.usuarios.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repository;

    public List<Producto> getAll() {
        return repository.findAll();
    }

    public Producto getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Producto save(Producto producto) {
        return repository.save(producto);
    }

    public Producto update(int id, Producto actualizado) {
        return repository.findById(id).map(p -> {
            p.setNombre(actualizado.getNombre());
            p.setDescripcion(actualizado.getDescripcion());
            p.setTalla(actualizado.getTalla());
            p.setPrecio(actualizado.getPrecio());
            p.setStock(actualizado.getStock());
            return repository.save(p);
        }).orElse(null);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Producto actualizarStock(int id, int nuevoStock) {
        return repository.findById(id).map(p -> {
            p.setStock(nuevoStock);
            return repository.save(p);
        }).orElse(null);
    }

    public Producto aplicarDescuento(int id, float porcentaje) {
        return repository.findById(id).map(p -> {
            float nuevoPrecio = p.getPrecio() * (1 - porcentaje / 100);
            p.setPrecio(nuevoPrecio);
            return repository.save(p);
        }).orElse(null);
    }
}

