package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.Collections;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class CuerpoPedido {
	
	@NotNull
	private Integer ordenId;
	
	@NotBlank
	private String proveedorRuc;
	
	@NotEmpty
	private ArrayList<LineaPedido> lineasPedido;

	public CuerpoPedido(@NotBlank Integer ordenId, @NotBlank String proveedorRuc, @NotEmpty ArrayList<LineaPedido> lineasPedido) {
		super();
		this.ordenId = ordenId;
		this.proveedorRuc = proveedorRuc;
		this.lineasPedido = lineasPedido;
	}

	public Integer getOrdenId() {
		return ordenId;
	}

	public void setOrdenId(Integer ordenId) {
		this.ordenId = ordenId;
	}

	public String getProveedorRuc() {
		return proveedorRuc;
	}

	public void setProveedorRuc(String proveedorRuc) {
		this.proveedorRuc = proveedorRuc;
	}

	public ArrayList<LineaPedido> getLineasPedido() {
		return lineasPedido;
	}

	public void setLineasPedido(ArrayList<LineaPedido> lineasPedido) {
		this.lineasPedido = lineasPedido;
	}
	
	public boolean hayRepetidos() {
		ArrayList<String> productos = new ArrayList<String>();
		for (LineaPedido lineaPedido : lineasPedido) {
			productos.add(lineaPedido.getProductoCodigo());
		}
		Collections.sort(productos);
		String codigoAux = "";
		for (String codigo : productos) {
			if (codigoAux.contentEquals(codigo)) {
				return true;
			} else {
				codigoAux = codigo;
			}
		}
		return false;
	}

}
