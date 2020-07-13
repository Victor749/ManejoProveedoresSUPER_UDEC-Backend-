package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.modelo.DevolucionProveedorProducto;
import com.example.demo.modelo.DevolucionProveedorProductoId;

public interface DevolucionProveedorProductoRepositorio extends JpaRepository<DevolucionProveedorProducto, DevolucionProveedorProductoId> {
	
	@Query (value = "select * from devolucion_proveedor_producto where devolucion_id = ?1", nativeQuery = true )
	Iterable<DevolucionProveedorProducto> lineasDeDevolucion(Integer idDevolucion);
	
}
