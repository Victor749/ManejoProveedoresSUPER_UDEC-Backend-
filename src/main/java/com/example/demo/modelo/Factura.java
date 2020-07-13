package com.example.demo.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "factura")
public class Factura {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "factura_id")
	private int id;
	
	@OneToOne 
    @JoinColumn (name = "pedido_id")
    Pedido pedido;
	
	// US Dollars
	@Column (name = "factura_total_usd")
	private BigDecimal total;
	
	@Column (name = "factura_detalle")
	private String detalle;
	
	// US Dollars
	@Column (name = "factura_total_estimado_usd")
	private BigDecimal totalEstimado;
	
	@Column (name = "factura_detalle_estimado")
	private String detalleEstimado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public BigDecimal getTotalEstimado() {
		return totalEstimado;
	}

	public void setTotalEstimado(BigDecimal totalEstimado) {
		this.totalEstimado = totalEstimado;
	}

	public String getDetalleEstimado() {
		return detalleEstimado;
	}

	public void setDetalleEstimado(String detalleEstimado) {
		this.detalleEstimado = detalleEstimado;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	
}
