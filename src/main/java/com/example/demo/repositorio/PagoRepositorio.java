package com.example.demo.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.modelo.Pago;

public interface PagoRepositorio extends JpaRepository<Pago, Integer> {
	@Query(value = "select * from pago where factura_id = ?1", nativeQuery = true)
	Optional<Pago> encontrarPagoPorFactura(Integer facturaId);
}
