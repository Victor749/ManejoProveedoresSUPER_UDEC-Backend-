package com.example.demo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name = "producto")
public class Producto {
	
	@Column (name = "producto_nombre")
	@NotBlank
	private String nombre;
	
	// kg
	@Column (name = "producto_peso_kg", nullable = false)
	private float peso;
	
	// m3
	@Column (name = "producto_volumen_m3", nullable = false)
	private float volumen;
	
	@Id
	@Column (name = "producto_codigo")
	@NotBlank
	private String codigo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public float getVolumen() {
		return volumen;
	}

	public void setVolumen(float volumen) {
		this.volumen = volumen;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
