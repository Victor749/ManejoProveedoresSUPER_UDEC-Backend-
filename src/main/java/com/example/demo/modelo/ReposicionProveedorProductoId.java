package com.example.demo.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class ReposicionProveedorProductoId implements Serializable{

	@Column (name = "reposicion_id")
    int reposicionId;
	
	@Column (name = "proveedor_ruc")
    String proveedorRuc;
	
	@Column (name = "producto_codigo")
	String productoCodigo;
	
	private ReposicionProveedorProductoId() {}

	public ReposicionProveedorProductoId(int reposicionId, String proveedorRuc, String productoCodigo) {
		this.reposicionId = reposicionId;
		this.proveedorRuc = proveedorRuc;
		this.productoCodigo = productoCodigo;
	}

	public int getReposicionId() {
		return reposicionId;
	}

	public String getProveedorRuc() {
		return proveedorRuc;
	}

	public String getProductoCodigo() {
		return productoCodigo;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ReposicionProveedorProductoId that = (ReposicionProveedorProductoId) o;
        return Objects.equals(reposicionId, that.reposicionId) &&
        	   Objects.equals(proveedorRuc, that.proveedorRuc) &&
               Objects.equals(productoCodigo, that.productoCodigo);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(reposicionId, proveedorRuc, productoCodigo);
    }
	
}
