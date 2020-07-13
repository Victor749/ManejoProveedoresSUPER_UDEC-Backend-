package com.example.demo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "devolucion")
public class Devolucion {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "devolucion_id")
	private int id;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "devolucion_fecha_hora")
	private String fechaHora;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "devolucion_fecha_hora_retiro")
	private String fechaHoraRetiro;
	
	@Column (name = "devolucion_descripcion")
	private String descripcion;
	
	@Column (name = "devolucion_aprobada")
	private boolean aprobada;
	
	@Column (name = "devolucion_retirada")
	private boolean retirada;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getFechaHoraRetiro() {
		return fechaHoraRetiro;
	}

	public void setFechaHoraRetiro(String fechaHoraRetiro) {
		this.fechaHoraRetiro = fechaHoraRetiro;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isAprobada() {
		return aprobada;
	}

	public void setAprobada(boolean aprobada) {
		this.aprobada = aprobada;
	}

	public boolean isRetirada() {
		return retirada;
	}

	public void setRetirada(boolean retirada) {
		this.retirada = retirada;
	}

}
