package com.example.demo.controlador;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.modelo.CuerpoEntrega;
import com.example.demo.modelo.CuerpoPedido;
import com.example.demo.modelo.Factura;
import com.example.demo.modelo.LineaPedido;
import com.example.demo.modelo.Mensaje;
import com.example.demo.modelo.Pago;
import com.example.demo.modelo.Pedido;
import com.example.demo.modelo.PedidoProveedorProducto;
import com.example.demo.modelo.Proveedor;
import com.example.demo.modelo.ProveedorProducto;
import com.example.demo.modelo.ProveedorProductoId;
import com.example.demo.repositorio.FacturaRepositorio;
import com.example.demo.repositorio.OrdenRepositorio;
import com.example.demo.repositorio.PagoRepositorio;
import com.example.demo.repositorio.ProveedorRepositorio;
import com.example.demo.repositorio.PedidoProveedorProductoRepositorio;
import com.example.demo.repositorio.PedidoRepositorio;
import com.example.demo.repositorio.ProveedorProductoRepositorio;

@RestController
@RequestMapping ("/api/pedidos")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH})
public class PedidoControlador {
	
	@Autowired
	PedidoRepositorio pedidoRepositorio;
	
	@Autowired
	ProveedorProductoRepositorio proveedorProductoRepositorio;
	
	@Autowired
	PedidoProveedorProductoRepositorio pedidoProveedorProductoRepositorio;
	
	@Autowired
	FacturaRepositorio facturaRepositorio;
	
	@Autowired
	PagoRepositorio pagoRepositorio;
	
	@Autowired
	OrdenRepositorio ordenRepositorio;
	
	@Autowired
	ProveedorRepositorio proveedorRepositorio;
	
	@GetMapping
	public Iterable<Pago> obtenerTodos() {
		return pagoRepositorio.findAll();
	}
	
	@GetMapping ("/{idPago}")
	public Pago obtenerPorId(@PathVariable (value = "idPago") Integer idPago) {
		return pagoRepositorio.findById(idPago).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido No Encontrado.");
		});
	}
	
	@GetMapping ("/orden/{idOrden}")
	public Iterable<Pedido> obtenerPorOrden(@PathVariable (value = "idOrden") Integer idOrden) {
		return pedidoRepositorio.encontrarPedidoPorOrden(idOrden);
	}
	
	@GetMapping ("/{idPedido}/detalle")
	public Iterable<PedidoProveedorProducto> detallePedido(@PathVariable (value = "idPedido") Integer idPedido) {
		return pedidoProveedorProductoRepositorio.lineasDePedido(idPedido);
	}
	
	@PostMapping ("/registro")
	@Transactional
	public Mensaje registrarPedido(@Valid @RequestBody CuerpoPedido cuerpoPedido) {
		Mensaje mensaje = new Mensaje();
		if (!cuerpoPedido.hayRepetidos()) {
			int tiempoEnvio = -1;
			BigDecimal costoTotal = new BigDecimal("0.00");
			String detalle = "Producto : Costo Unitario : Cantidad : Subtotal\n";
			ArrayList<PedidoProveedorProducto> detallePedido = new ArrayList<PedidoProveedorProducto>();
			for (LineaPedido lineaPedido : cuerpoPedido.getLineasPedido()) {
				ProveedorProductoId proveedorProductoId = new ProveedorProductoId (cuerpoPedido.getProveedorRuc(), lineaPedido.getProductoCodigo());
				ProveedorProducto proveedorProducto = proveedorProductoRepositorio.findById(proveedorProductoId).get();
				if (proveedorProducto.getTiempoEnvio() > tiempoEnvio) {
					tiempoEnvio = proveedorProducto.getTiempoEnvio();
				}
				BigDecimal subtotal = proveedorProducto.getCostoUnitario().multiply(new BigDecimal(lineaPedido.getCantidad()));
				costoTotal = costoTotal.add(subtotal);
				detalle += "\n" + proveedorProductoId.getProductoCodigo() + " : " + proveedorProducto.getCostoUnitario() + " : " + lineaPedido.getCantidad() + " : " + subtotal; 
				PedidoProveedorProducto pedidoProveedorProducto = new PedidoProveedorProducto();
				pedidoProveedorProducto.setProveedorProducto(proveedorProducto);
				pedidoProveedorProducto.setCantidadEsperada(lineaPedido.getCantidad());
				pedidoProveedorProducto.setCostoUnitario(proveedorProducto.getCostoUnitario());
				detallePedido.add(pedidoProveedorProducto);
			}
			detalle += "\n\nTotal: " + costoTotal;
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date fechaHoraActual = cal.getTime();
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + tiempoEnvio);
			Date fechaHoraEstimada = cal.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
			Pedido pedido = new Pedido();
			pedido.setFechaHora(formatter.format(fechaHoraActual));
			pedido.setFechaHoraEntregaEstimada(formatter.format(fechaHoraEstimada));
			pedido.setRecibido(false);
			pedido.setOrden(ordenRepositorio.findById(cuerpoPedido.getOrdenId()).get());
			Pedido pedidoGuardado = pedidoRepositorio.save(pedido);
			for (PedidoProveedorProducto pedidoProveedorProducto : detallePedido) {
				pedidoProveedorProducto.setPedido(pedidoGuardado);
				pedidoProveedorProducto.setId();
				pedidoProveedorProductoRepositorio.save(pedidoProveedorProducto);
			}
			Factura factura = new Factura();
			factura.setTotalEstimado(costoTotal);
			factura.setDetalleEstimado(detalle);
			factura.setPedido(pedidoGuardado);
			Factura facturaGuardada = facturaRepositorio.save(factura);
			Pago pago = new Pago();
			pago.setFactura(facturaGuardada);
			pago.setCancelado(false);
			pago.setAutorizado(false);
			pagoRepositorio.save(pago);
			mensaje.setMensaje(detalle);
		} else {
			mensaje.setMensaje("Existen Elementos Repetidos.");
		}
		return mensaje;
	}
	
	@PutMapping ("/entrega")
	@Transactional
	public Mensaje registrarEntrega(@Valid @RequestBody CuerpoEntrega cuerpoEntrega) {
		Mensaje mensaje = new Mensaje();
		if (cuerpoEntrega.getTotal().matches("[0-9]+[.]{1}+[0-9]{2}")) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date fechaHoraActual = cal.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
			Pago pago = pagoRepositorio.encontrarPagoPorFactura(cuerpoEntrega.getFacturaId()).get();
			pago.getFactura().setTotal(new BigDecimal(cuerpoEntrega.getTotal()));
			pago.getFactura().setDetalle(cuerpoEntrega.getDetalle());
			pago.setFechaHoraGeneracion(formatter.format(fechaHoraActual));
			pago.getFactura().getPedido().setFechaHoraEntrega(formatter.format(fechaHoraActual));
			pago.getFactura().getPedido().setRecibido(true);
			pago.setAutorizado(true);
			pagoRepositorio.save(pago);
			mensaje.setMensaje("Registro de Entrega Exitoso.");
		} else {
			mensaje.setMensaje("Total Inválido.");
		}
		return mensaje;
	}
	
	@PatchMapping ("/pago/{idPago}")
	@Transactional
	public Mensaje hacerPago(@PathVariable (value = "idPago") Integer idPago) {
		Mensaje mensaje = new Mensaje();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date fechaHoraActual = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Pago pago = pagoRepositorio.encontrarPagoPorFactura(idPago).get();
		if (pago.isAutorizado()) {
			pago.setFechaHoraCancelacion(formatter.format(fechaHoraActual));
			pago.setCancelado(true);
			Proveedor proveedor = proveedorRepositorio.findById(pedidoProveedorProductoRepositorio.lineaDePedido(pago.getFactura().getPedido().getId()).get().getId().getProveedorRuc()).get();
			proveedor.setSaldo(proveedor.getSaldo().add(pago.getFactura().getTotal()));
			proveedorRepositorio.save(proveedor);
			pagoRepositorio.save(pago);
			mensaje.setMensaje("Pago Exitoso.");
		} else {
			mensaje.setMensaje("Pago No Autorizado.");
		}
		return mensaje;
	}
	
}
