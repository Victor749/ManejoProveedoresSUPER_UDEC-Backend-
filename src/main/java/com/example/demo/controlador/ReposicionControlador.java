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

import com.example.demo.modelo.CuerpoReposicion;
import com.example.demo.modelo.LineaReposicion;
import com.example.demo.modelo.Mensaje;
import com.example.demo.modelo.ProveedorProducto;
import com.example.demo.modelo.ProveedorProductoId;
import com.example.demo.modelo.Reposicion;
import com.example.demo.modelo.ReposicionProveedorProducto;
import com.example.demo.repositorio.ProveedorProductoRepositorio;
import com.example.demo.repositorio.ReposicionProveedorProductoRepositorio;
import com.example.demo.repositorio.ReposicionRepositorio;

@RestController
@RequestMapping ("/api/reposiciones")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
public class ReposicionControlador {
	
	@Autowired
	ReposicionRepositorio reposicionRepositorio;
	
	@Autowired
	ProveedorProductoRepositorio proveedorProductoRepositorio;
	
	@Autowired
	ReposicionProveedorProductoRepositorio reposicionProveedorProductoRepositorio;
	
	@GetMapping
	public Iterable<Reposicion> obtenerTodos() {
		return reposicionRepositorio.findAll();
	}
	
	@GetMapping ("/{idReposicion}")
	public Reposicion obtenerPorId(@PathVariable (value = "idReposicion") Integer idReposicion) {
		return reposicionRepositorio.findById(idReposicion).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reposición No Encontrada.");
		});
	}
	
	@GetMapping ("/{idReposicion}/detalle")
	public Iterable<ReposicionProveedorProducto> detallePedido(@PathVariable (value = "idReposicion") Integer idReposicion) {
		return reposicionProveedorProductoRepositorio.lineasDeReposicion(idReposicion);
	}
	
	@PostMapping ("/registro")
	@Transactional
	public Mensaje registrarReposicion(@Valid @RequestBody CuerpoReposicion cuerpoReposicion) {
		Mensaje mensaje = new Mensaje();
		if (!cuerpoReposicion.hayRepetidos()) {
			int tiempoEnvio = -1;
			ArrayList<ReposicionProveedorProducto> detalleReposicion = new ArrayList<ReposicionProveedorProducto>();
			for (LineaReposicion lineaReposicion : cuerpoReposicion.getLineasReposicion()) {
				ProveedorProductoId proveedorProductoId = new ProveedorProductoId (cuerpoReposicion.getProveedorRuc(), lineaReposicion.getProductoCodigo());
				ProveedorProducto proveedorProducto = proveedorProductoRepositorio.findById(proveedorProductoId).get();
				if (proveedorProducto.getTiempoEnvio() > tiempoEnvio) {
					tiempoEnvio = proveedorProducto.getTiempoEnvio();
				}
				ReposicionProveedorProducto reposicionProveedorProducto = new ReposicionProveedorProducto();
				reposicionProveedorProducto.setProveedorProducto(proveedorProducto);
				reposicionProveedorProducto.setCantidadEsperada(lineaReposicion.getCantidad());
				detalleReposicion.add(reposicionProveedorProducto);
			}
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			Date fechaHoraActual = cal.getTime();
			cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) + tiempoEnvio);
			Date fechaHoraEstimada = cal.getTime();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
			Reposicion reposicion = new Reposicion();
			reposicion.setFechaHora(formatter.format(fechaHoraActual));
			reposicion.setFechaHoraEntregaEstimada(formatter.format(fechaHoraEstimada));
			reposicion.setEntregada(false);
			reposicion.setAprobada(false);
			reposicion.setDescripcion(cuerpoReposicion.getDescripcion());
			Reposicion reposicionGuardada = reposicionRepositorio.save(reposicion);
			for (ReposicionProveedorProducto reposicionProveedorProducto : detalleReposicion) {
				reposicionProveedorProducto.setReposicion(reposicionGuardada);
				reposicionProveedorProducto.setId();
				reposicionProveedorProductoRepositorio.save(reposicionProveedorProducto);
			}
			mensaje.setMensaje("Regsitro de Reposición Exitoso.");
		} else {
			mensaje.setMensaje("Existen Elementos Repetidos.");
		}
		return mensaje;
	}
	
	@PatchMapping ("/aprobar/{idReposicion}")
	@Transactional
	public Reposicion aprobar(@PathVariable (value = "idReposicion") Integer idReposicion) {
		Reposicion reposicion = reposicionRepositorio.findById(idReposicion).get();
		reposicion.setAprobada(true);
		return reposicionRepositorio.save(reposicion);
	}
	
	@PatchMapping ("/entrega/{idReposicion}")
	@Transactional
	public Mensaje retirar(@PathVariable (value = "idReposicion") Integer idReposicion) {
		Mensaje mensaje = new Mensaje();
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Date fechaHoraActual = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Reposicion reposicion = reposicionRepositorio.findById(idReposicion).get();
		if (reposicion.isAprobada()) {
			reposicion.setEntregada(true);
			reposicion.setFechaHoraEntrega(formatter.format(fechaHoraActual));
			reposicionRepositorio.save(reposicion);
			mensaje.setMensaje("Reposición Exitosa.");
		} else {
			mensaje.setMensaje("Reposición No Aprobada.");
		}
		return mensaje;
	}

}
