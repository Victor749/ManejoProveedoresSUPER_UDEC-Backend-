package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.Collections;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CuerpoDevolucion {
	
	@NotBlank
	private String proveedorRuc;
	
	@NotBlank
	private String descripcion;
	
	@NotEmpty
	private ArrayList<LineaDevolucion> lineasDevolucion;

	public CuerpoDevolucion(@NotBlank String proveedorRuc, @NotBlank String descripcion,
			@NotEmpty ArrayList<LineaDevolucion> lineasDevolucion) {
		super();
		this.proveedorRuc = proveedorRuc;
		this.descripcion = descripcion;
		this.lineasDevolucion = lineasDevolucion;
	}

	public String getProveedorRuc() {
		return proveedorRuc;
	}

	public void setProveedorRuc(String proveedorRuc) {
		this.proveedorRuc = proveedorRuc;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<LineaDevolucion> getLineasDevolucion() {
		return lineasDevolucion;
	}

	public void setLineasDevolucion(ArrayList<LineaDevolucion> lineasDevolucion) {
		this.lineasDevolucion = lineasDevolucion;
	}

	public boolean hayRepetidos() {
		ArrayList<String> productos = new ArrayList<String>();
		for (LineaDevolucion lineaDevolucion : lineasDevolucion) {
			productos.add(lineaDevolucion.getProductoCodigo());
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
