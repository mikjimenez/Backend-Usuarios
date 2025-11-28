package com.usuarios.service;

import com.usuarios.model.Pedido;
import com.usuarios.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public List<Pedido> getAll() {
        return repository.findAll();
    }

    public Pedido getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Pedido save(Pedido pedido) {
        pedido.setEstado("Pendiente");
        return repository.save(pedido);
    }

    public Pedido update(int id, Pedido nuevo) {
        return repository.findById(id).map(p -> {
            p.setClienteId(nuevo.getClienteId());
            p.setProductos(nuevo.getProductos());
            p.setEstado(nuevo.getEstado());
            p.setTotal(nuevo.getTotal());
            return repository.save(p);
        }).orElse(null);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Pedido actualizarEstado(int id, String nuevoEstado) {
        return repository.findById(id).map(p -> {
            p.setEstado(nuevoEstado);
            return repository.save(p);
        }).orElse(null);
    }
}
