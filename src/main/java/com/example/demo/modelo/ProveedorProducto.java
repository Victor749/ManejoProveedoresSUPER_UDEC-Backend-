package com.example.demo.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "proveedor_producto")
public class ProveedorProducto {
	
	@EmbeddedId
    private ProveedorProductoId id;
	
	@ManyToOne 
	@MapsId("proveedorRuc")
    @JoinColumn (name = "proveedor_ruc")
	@JsonIgnore
    Proveedor proveedor;
 
    @ManyToOne 
    @MapsId("productoCodigo")
    @JoinColumn (name = "producto_codigo")
    @JsonIgnore
    Producto producto;

	// US Dollars
	@Column (name = "proveedor_producto_costo_unitario_usd")
	@NotNull
	private BigDecimal costoUnitario;
	
	// Horas
	@Column (name = "proveedor_producto_tiempo_envio", nullable = false)
	private int tiempoEnvio;

	public ProveedorProductoId getId() {
		return id;
	}

	public void setId() {
		this.id = new ProveedorProductoId(proveedor.getRuc(), producto.getCodigo());
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public BigDecimal getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(BigDecimal costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public int getTiempoEnvio() {
		return tiempoEnvio;
	}

	public void setTiempoEnvio(int tiempoEnvio) {
		this.tiempoEnvio = tiempoEnvio;
	}

}
