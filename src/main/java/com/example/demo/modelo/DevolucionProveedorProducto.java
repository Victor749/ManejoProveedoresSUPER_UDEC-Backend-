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
@Table (name = "devolucion_proveedor_producto")
public class DevolucionProveedorProducto {

	@EmbeddedId
    private DevolucionProveedorProductoId id;
	
	@ManyToOne 
	@MapsId("devolucionId")
    @JoinColumn (name = "devolucion_id")
	@JsonIgnore
    Devolucion devolucion;
	
	@ManyToOne 
	@JoinColumns({
		@JoinColumn(name="proveedor_ruc", insertable=false, updatable=false, referencedColumnName="proveedor_ruc"),
		@JoinColumn(name="producto_codigo", insertable=false, updatable=false, referencedColumnName="producto_codigo")
    })
	@JsonIgnore
    ProveedorProducto proveedorProducto;
	
	@Column (name = "devolucion_proveedor_producto_cantidad_esperada", nullable = false)
	private int cantidadEsperada;

	public DevolucionProveedorProductoId getId() {
		return id;
	}

	public void setId() {
		this.id = new DevolucionProveedorProductoId(devolucion.getId(), proveedorProducto.getId().getProveedorRuc(), proveedorProducto.getId().getProductoCodigo());
	}

	public Devolucion getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Devolucion devolucion) {
		this.devolucion = devolucion;
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
