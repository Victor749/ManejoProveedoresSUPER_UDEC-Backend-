package com.example.demo.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProveedorProductoId implements Serializable {
 
    @Column (name = "proveedor_ruc")
    String proveedorRuc;
 
    @Column (name = "producto_codigo")
    String productoCodigo;
 
    private ProveedorProductoId() {}
 
    public ProveedorProductoId(String proveedorRuc, String productoCodigo) {
        this.proveedorRuc = proveedorRuc;
        this.productoCodigo = productoCodigo;
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
 
        ProveedorProductoId that = (ProveedorProductoId) o;
        return Objects.equals(proveedorRuc, that.proveedorRuc) &&
               Objects.equals(productoCodigo, that.productoCodigo);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(proveedorRuc, productoCodigo);
    }
    
}
