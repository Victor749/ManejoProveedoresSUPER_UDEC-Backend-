package com.example.demo.modelo;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table (name = "proveedor")
public class Proveedor {
	
	@Column (name = "proveedor_nombre")
	@NotBlank
	private String nombre;
	
	@Id
	@Column (name = "proveedor_ruc")
	@NotBlank
	private String ruc;
	
	@Column (name = "proveedor_email")
	@NotBlank
	private String email;
	
	@Column (name = "proveedor_direccion")
	@NotBlank
	private String direccion;
	
	@Column (name = "proveedor_telefono")
	@NotBlank
	private String telefono;
	
	@Column (name = "proveedor_codigo_pago")
	@NotBlank
	private String codigoPago;
	
	// US Dollars
	@Column (name = "proveedor_saldo_usd")
	private BigDecimal saldo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getCodigoPago() {
		return codigoPago;
	}

	public void setCodigoPago(String codigoPago) {
		this.codigoPago = codigoPago;
	}

}
