package com.example.demo.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProveedorProductoId implements Serializable{

	@Column (name = "pedido_id")
    int pedidoId;
	
	@Column (name = "proveedor_ruc")
    String proveedorRuc;
	
	@Column (name = "producto_codigo")
	String productoCodigo;
	
	private PedidoProveedorProductoId() {}

	public PedidoProveedorProductoId(int pedidoId, String proveedorRuc, String productoCodigo) {
		this.pedidoId = pedidoId;
		this.proveedorRuc = proveedorRuc;
		this.productoCodigo = productoCodigo;
	}

	public int getPedidoId() {
		return pedidoId;
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
 
        PedidoProveedorProductoId that = (PedidoProveedorProductoId) o;
        return Objects.equals(pedidoId, that.pedidoId) &&
        	   Objects.equals(proveedorRuc, that.proveedorRuc) &&
               Objects.equals(productoCodigo, that.productoCodigo);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(pedidoId, proveedorRuc, productoCodigo);
    }
	
}
