package com.example.demo.controlador;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.modelo.CuerpoDevolucion;
import com.example.demo.modelo.Devolucion;
import com.example.demo.modelo.DevolucionProveedorProducto;
import com.example.demo.modelo.LineaDevolucion;
import com.example.demo.modelo.Mensaje;
import com.example.demo.modelo.ProveedorProducto;
import com.example.demo.modelo.ProveedorProductoId;
import com.example.demo.repositorio.DevolucionProveedorProductoRepositorio;
import com.example.demo.repositorio.DevolucionRepositorio;
import com.example.demo.repositorio.ProveedorProductoRepositorio;

@RestController
@RequestMapping ("/api/devoluciones")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
public class DevolucionControlador {
	
	@Autowired
	DevolucionRepositorio devolucionRepositorio;
	
	@Autowired
	ProveedorProductoRepositorio proveedorProductoRepositorio;
	
	@Autowired
	DevolucionProveedorProductoRepositorio devolucionProveedorProductoRepositorio;
	
	@GetMapping
	public Iterable<Devolucion> obtenerTodos() {
		return devolucionRepositorio.findAll();
	}
	
	@GetMapping ("/{idDevolucion}")
	public Devolucion obtenerPorId(@PathVariable (value = "idDevolucion") Integer idDevolucion) {
		return devolucionRepositorio.findById(idDevolucion).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Devolución No Encontrada.");
		});
	}
	
	@GetMapping ("/{idDevolucion}/detalle")
	public Iterable<DevolucionProveedorProducto> detallePedido(@PathVariable (value = "idDevolucion") Integer idDevolucion) {
		return devolucionProveedorProductoRepositorio.lineasDeDevolucion(idDevolucion);
	}
	
	@PostMapping ("/registro")
	@Transactional
	public Mensaje registrarDevolucion(@Valid @RequestBody CuerpoDevolucion cuerpoDevolucion) {
		Mensaje mensaje = new Mensaje();
		if (!cuerpoDevolucion.hayRepetidos()) {
			ArrayList<DevolucionProveedorProducto> detalleDevolucion = new ArrayList<DevolucionProveedorProducto>();
			for (LineaDevolucion lineaDevolucion : cuerpoDevolucion.getLineasDevolucion()) {
				ProveedorProductoId proveedorProductoId = new ProveedorProductoId (cuerpoDevolucion.getProveedorRuc(), lineaDevolucion.getProductoCodigo());
				ProveedorProducto proveedorProducto = proveedorProductoRepositorio.findById(proveedorProductoId).get();
				DevolucionProveedorProducto devolucionProveedorProducto = new DevolucionProveedorProducto();
				devolucionProveedorProducto.setProveedorProducto(proveedorProducto);
				devolucionProveedorProducto.setCantidadEsperada(lineaDevolucion.getCantidad());
				detalleDevolucion.add(devolucionProveedorProducto);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date fechaHoraActual = cal.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
			Devolucion devolucion = new Devolucion();
			devolucion.setFechaHora(formatter.format(fechaHoraActual));
			devolucion.setAprobada(false);
			devolucion.setDescripcion(cuerpoDevolucion.getDescripcion());
			devolucion.setRetirada(false);
			Devolucion devolucionGuardada = devolucionRepositorio.save(devolucion);
			for (DevolucionProveedorProducto devolucionProveedorProducto : detalleDevolucion) {
				devolucionProveedorProducto.setDevolucion(devolucionGuardada);
				devolucionProveedorProducto.setId();
				devolucionProveedorProductoRepositorio.save(devolucionProveedorProducto);
			}
			mensaje.setMensaje("Registro de Devolución Exitoso.");
		} else {
			mensaje.setMensaje("Existen Elementos Repetidos.");
		}
		return mensaje;
	}
	
	@PatchMapping ("/aprobar/{idDevolucion}")
	@Transactional
	public Devolucion aprobar(@PathVariable (value = "idDevolucion") Integer idDevolucion) {
		Devolucion devolucion = devolucionRepositorio.findById(idDevolucion).get();
		devolucion.setAprobada(true);
		return devolucionRepositorio.save(devolucion);
	}
	
	@PatchMapping ("/retiro/{idDevolucion}")
	@Transactional
	public Mensaje retirar(@PathVariable (value = "idDevolucion") Integer idDevolucion) {
		Mensaje mensaje = new Mensaje();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date fechaHoraActual = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Devolucion devolucion = devolucionRepositorio.findById(idDevolucion).get();
		if (devolucion.isAprobada()) {
			devolucion.setRetirada(true);
			devolucion.setFechaHoraRetiro(formatter.format(fechaHoraActual));
			devolucionRepositorio.save(devolucion);
			mensaje.setMensaje("Devolución Exitosa.");
		} else {
			mensaje.setMensaje("Devolución No Aprobada.");
		}
		return mensaje;
	}
	
}
