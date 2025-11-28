package com.usuarios.service;

import com.usuarios.model.Pago;
import com.usuarios.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository repository;

    public List<Pago> getAll() {
        return repository.findAll();
    }

    public Pago getById(int id) {
        return repository.findById(id).orElse(null);
    }

    public Pago save(Pago pago) {
        pago.setFecha(LocalDateTime.now());
        pago.setEstado("Pendiente");
        return repository.save(pago);
    }

    public Pago update(int id, Pago nuevo) {
        return repository.findById(id).map(p -> {
            p.setPedidoId(nuevo.getPedidoId());
            p.setMetodo(nuevo.getMetodo());
            p.setMonto(nuevo.getMonto());
            p.setEstado(nuevo.getEstado());
            p.setFecha(nuevo.getFecha());
            return repository.save(p);
        }).orElse(null);
    }

    public void delete(int id) {
        repository.deleteById(id);
    }

    public Pago actualizarEstado(int id, String nuevoEstado) {
        return repository.findById(id).map(p -> {
            p.setEstado(nuevoEstado);
            return repository.save(p);
        }).orElse(null);
    }
}