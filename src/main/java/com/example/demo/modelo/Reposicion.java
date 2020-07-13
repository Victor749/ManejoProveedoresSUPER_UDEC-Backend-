package com.example.demo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "reposicion")
public class Reposicion {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "reposicion_id")
	private int id;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "reposicion_fecha_hora")
	private String fechaHora;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "reposicion_fecha_hora_entrega")
	private String fechaHoraEntrega;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "reposicion_fecha_hora_entrega_estimada")
	private String fechaHoraEntregaEstimada;
	
	@Column (name = "reposicion_descripcion")
	private String descripcion;
	
	@Column (name = "reposicion_aprobada")
	private boolean aprobada;
	
	@Column (name = "reposicion_entregada")
	private boolean entregada;

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

	public String getFechaHoraEntrega() {
		return fechaHoraEntrega;
	}

	public void setFechaHoraEntrega(String fechaHoraEntrega) {
		this.fechaHoraEntrega = fechaHoraEntrega;
	}

	public String getFechaHoraEntregaEstimada() {
		return fechaHoraEntregaEstimada;
	}

	public void setFechaHoraEntregaEstimada(String fechaHoraEntregaEstimada) {
		this.fechaHoraEntregaEstimada = fechaHoraEntregaEstimada;
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

	public boolean isEntregada() {
		return entregada;
	}

	public void setEntregada(boolean entregada) {
		this.entregada = entregada;
	}

}
