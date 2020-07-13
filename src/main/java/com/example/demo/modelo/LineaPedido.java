package com.example.demo.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LineaPedido {
	
	@NotBlank
	private String productoCodigo;
	
	@NotNull
	private Integer cantidad;

	public LineaPedido(@NotBlank String productoCodigo, @NotNull Integer cantidad) {
		super();
		this.productoCodigo = productoCodigo;
		this.cantidad = cantidad;
	}

	public String getProductoCodigo() {
		return productoCodigo;
	}

	public void setProductoCodigo(String productoCodigo) {
		this.productoCodigo = productoCodigo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
}
