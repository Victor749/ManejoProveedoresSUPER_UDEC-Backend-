package com.example.demo.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.modelo.PedidoProveedorProducto;
import com.example.demo.modelo.PedidoProveedorProductoId;

public interface PedidoProveedorProductoRepositorio extends JpaRepository<PedidoProveedorProducto, PedidoProveedorProductoId> {
	
	@Query (value = "select * from pedido_proveedor_producto where pedido_id = ?1", nativeQuery = true )
	Iterable<PedidoProveedorProducto> lineasDePedido(Integer idPedido);
	
	@Query (value = "select * from pedido_proveedor_producto where pedido_id = ?1 limit 1", nativeQuery = true )
	Optional<PedidoProveedorProducto> lineaDePedido(Integer idPedido);
	
}
