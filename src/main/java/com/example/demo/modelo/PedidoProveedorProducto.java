package com.example.demo.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table (name = "pedido_proveedor_producto")
public class PedidoProveedorProducto {
	
	@EmbeddedId
    private PedidoProveedorProductoId id;
	
	@ManyToOne 
	@MapsId("pedidoId")
    @JoinColumn (name = "pedido_id")
	@JsonIgnore
    Pedido pedido;
	
	@ManyToOne 
	@JoinColumns({
		@JoinColumn(name="proveedor_ruc", insertable=false, updatable=false, referencedColumnName="proveedor_ruc"),
		@JoinColumn(name="producto_codigo", insertable=false, updatable=false, referencedColumnName="producto_codigo")
    })
	@JsonIgnore
    ProveedorProducto proveedorProducto;
	
	// US Dollars
	@Column (name = "pedido_proveedor_producto_costo_unitario_usd")
	@NotNull
	private BigDecimal costoUnitario;
	
	@Column (name = "pedido_proveedor_producto_cantidad_esperada", nullable = false)
	private int cantidadEsperada;

	public PedidoProveedorProductoId getId() {
		return id;
	}

	public void setId() {
		this.id = new PedidoProveedorProductoId(pedido.getId(), proveedorProducto.getId().getProveedorRuc(), proveedorProducto.getId().getProductoCodigo());
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public ProveedorProducto getProveedorProducto() {
		return proveedorProducto;
	}

	public void setProveedorProducto(ProveedorProducto proveedorProducto) {
		this.proveedorProducto = proveedorProducto;
	}

	public BigDecimal getCostoUnitario() {
		return costoUnitario;
	}

	public void setCostoUnitario(BigDecimal costoUnitario) {
		this.costoUnitario = costoUnitario;
	}

	public int getCantidadEsperada() {
		return cantidadEsperada;
	}

	public void setCantidadEsperada(int cantidadEsperada) {
		this.cantidadEsperada = cantidadEsperada;
	}

}
