package com.example.demo.modelo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CuerpoEntrega {
	
	@NotNull
	private Integer facturaId;
	
	@NotBlank
	private String total;
	
	@NotBlank
	private String detalle;
	
	public CuerpoEntrega(@NotNull Integer idFactura, @NotBlank String total, @NotBlank String detalle) {
		this.facturaId = idFactura;
		this.total = total;
		this.detalle = detalle;
	}

	public Integer getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(Integer idFactura) {
		this.facturaId = idFactura;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
}
