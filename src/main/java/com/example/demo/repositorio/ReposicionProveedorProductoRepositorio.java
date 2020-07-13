package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.modelo.ReposicionProveedorProducto;
import com.example.demo.modelo.ReposicionProveedorProductoId;

public interface ReposicionProveedorProductoRepositorio extends JpaRepository<ReposicionProveedorProducto, ReposicionProveedorProductoId> {
	
	@Query (value = "select * from reposicion_proveedor_producto where reposicion_id = ?1", nativeQuery = true )
	Iterable<ReposicionProveedorProducto> lineasDeReposicion(Integer idReposicion);
	
}