package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.modelo.ProveedorProducto;
import com.example.demo.modelo.ProveedorProductoId;

public interface ProveedorProductoRepositorio extends JpaRepository<ProveedorProducto, ProveedorProductoId> {
	@Query(value = "select * from proveedor_producto where proveedor_ruc = ?1", nativeQuery = true)
	Iterable<ProveedorProducto> encontrarProveedorProductoPorProveedor(String proveedorId);
}
