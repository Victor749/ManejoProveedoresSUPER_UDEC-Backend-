package com.example.demo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table (name = "pedido")
public class Pedido {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "pedido_id")
	private int id;
	
	@OneToOne 
    @JoinColumn (name = "orden_id")
	@JsonIgnoreProperties ({"detalle", "fechaHora"})
    Orden orden;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "pedido_fecha_hora")
	private String fechaHora;

	// YYYY-MM-DD HH:MM:SS
	@Column (name = "pedido_fecha_hora_entrega")
	private String fechaHoraEntrega;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "pedido_fecha_hora_entrega_estimada")
	private String fechaHoraEntregaEstimada;
	
	@Column (name = "pedido_recibido")
	private boolean recibido;

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

	public boolean isRecibido() {
		return recibido;
	}

	public void setRecibido(boolean recibido) {
		this.recibido = recibido;
	}

	public Orden getOrden() {
		return orden;
	}

	public void setOrden(Orden orden) {
		this.orden = orden;
	}
	
}
