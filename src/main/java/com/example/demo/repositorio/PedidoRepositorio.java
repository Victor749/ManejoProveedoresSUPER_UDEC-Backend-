package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.modelo.Pedido;

public interface PedidoRepositorio extends JpaRepository<Pedido, Integer> {
	@Query(value = "select * from pedido where orden_id = ?1", nativeQuery = true)
	Iterable<Pedido> encontrarPedidoPorOrden(Integer ordenId);
}
