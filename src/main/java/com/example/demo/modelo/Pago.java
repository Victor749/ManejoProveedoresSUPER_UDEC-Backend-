package com.example.demo.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table (name = "pago")
public class Pago {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column (name = "pago_id")
	private int id;
	
	@OneToOne 
    @JoinColumn (name = "factura_id")
    Factura factura;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "pago_fecha_hora_generacion")
	private String fechaHoraGeneracion;
	
	// YYYY-MM-DD HH:MM:SS
	@Column (name = "pago_fecha_hora_cancelacion")
	private String fechaHoraCancelacion;
	
	@Column (name = "pago_cancelado")
	private boolean cancelado;
	
	@Column (name = "pago_autorizado")
	private boolean autorizado;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFechaHoraGeneracion() {
		return fechaHoraGeneracion;
	}

	public void setFechaHoraGeneracion(String fechaHoraGeneracion) {
		this.fechaHoraGeneracion = fechaHoraGeneracion;
	}

	public String getFechaHoraCancelacion() {
		return fechaHoraCancelacion;
	}

	public void setFechaHoraCancelacion(String fechaHoraCancelacion) {
		this.fechaHoraCancelacion = fechaHoraCancelacion;
	}

	public boolean isCancelado() {
		return cancelado;
	}

	public void setCancelado(boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public boolean isAutorizado() {
		return autorizado;
	}

	public void setAutorizado(boolean autorizado) {
		this.autorizado = autorizado;
	}
	
}
