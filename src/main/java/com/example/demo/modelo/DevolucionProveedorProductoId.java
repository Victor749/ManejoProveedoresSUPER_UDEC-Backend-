package com.example.demo.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;

public class DevolucionProveedorProductoId implements Serializable{

	@Column (name = "devolucion_id")
    int devolucionId;
	
	@Column (name = "proveedor_ruc")
    String proveedorRuc;
	
	@Column (name = "producto_codigo")
	String productoCodigo;
	
	private DevolucionProveedorProductoId() {}

	public DevolucionProveedorProductoId(int devolucionId, String proveedorRuc, String productoCodigo) {
		this.devolucionId = devolucionId;
		this.proveedorRuc = proveedorRuc;
		this.productoCodigo = productoCodigo;
	}

	public int getDevolucionId() {
		return devolucionId;
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
 
        DevolucionProveedorProductoId that = (DevolucionProveedorProductoId) o;
        return Objects.equals(devolucionId, that.devolucionId) &&
        	   Objects.equals(proveedorRuc, that.proveedorRuc) &&
               Objects.equals(productoCodigo, that.productoCodigo);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(devolucionId, proveedorRuc, productoCodigo);
    }
	
}
