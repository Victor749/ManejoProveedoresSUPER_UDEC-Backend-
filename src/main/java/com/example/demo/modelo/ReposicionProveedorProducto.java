package com.example.demo.modelo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "reposicion_proveedor_producto")
public class ReposicionProveedorProducto {
	
	@EmbeddedId
    private ReposicionProveedorProductoId id;
	
	@ManyToOne 
	@MapsId("reposicionId")
    @JoinColumn (name = "reposicion_id")
	@JsonIgnore
    Reposicion reposicion;
	
	@ManyToOne 
	@JoinColumns({
		@JoinColumn(name="proveedor_ruc", insertable=false, updatable=false, referencedColumnName="proveedor_ruc"),
		@JoinColumn(name="producto_codigo", insertable=false, updatable=false, referencedColumnName="producto_codigo")
    })
	@JsonIgnore
    ProveedorProducto proveedorProducto;
	
	@Column (name = "reposicion_proveedor_producto_cantidad_esperada", nullable = false)
	private int cantidadEsperada;

	public ReposicionProveedorProductoId getId() {
		return id;
	}

	public void setId() {
		this.id = new ReposicionProveedorProductoId(reposicion.getId(), proveedorProducto.getId().getProveedorRuc(), proveedorProducto.getId().getProductoCodigo());
	}

	public Reposicion getReposicion() {
		return reposicion;
	}

	public void setReposicion(Reposicion reposicion) {
		this.reposicion = reposicion;
	}

	public ProveedorProducto getProveedorProducto() {
		return proveedorProducto;
	}

	public void setProveedorProducto(ProveedorProducto proveedorProducto) {
		this.proveedorProducto = proveedorProducto;
	}

	public int getCantidadEsperada() {
		return cantidadEsperada;
	}

	public void setCantidadEsperada(int cantidadEsperada) {
		this.cantidadEsperada = cantidadEsperada;
	}

}
