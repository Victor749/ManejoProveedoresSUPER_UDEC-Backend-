package com.example.demo.modelo;

import java.util.ArrayList;
import java.util.Collections;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class CuerpoReposicion {

	@NotBlank
	private String proveedorRuc;
	
	@NotBlank
	private String descripcion;
	
	@NotEmpty
	private ArrayList<LineaReposicion> lineasReposicion;
	
	public CuerpoReposicion(@NotBlank String proveedorRuc, @NotBlank String descripcion,
			@NotEmpty ArrayList<LineaReposicion> lineasReposicion) {
		super();
		this.proveedorRuc = proveedorRuc;
		this.descripcion = descripcion;
		this.lineasReposicion = lineasReposicion;
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

	public ArrayList<LineaReposicion> getLineasReposicion() {
		return lineasReposicion;
	}

	public void setLineasReposicion(ArrayList<LineaReposicion> lineasReposicion) {
		this.lineasReposicion = lineasReposicion;
	}

	public boolean hayRepetidos() {
		ArrayList<String> productos = new ArrayList<String>();
		for (LineaReposicion lineaReposicion : lineasReposicion) {
			productos.add(lineaReposicion.getProductoCodigo());
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
