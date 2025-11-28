package com.usuarios.repository;

import com.usuarios.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
}

