package com.example.demo.controlador;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.modelo.Orden;
import com.example.demo.repositorio.OrdenRepositorio;

@RestController
@RequestMapping ("/api/ordenes")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class OrdenControlador {
	
	@Autowired
	OrdenRepositorio ordenRepositorio;
	
	@GetMapping
	public Iterable<Orden> obtenerTodos() {
		return ordenRepositorio.findAll();
	}
	
	@GetMapping ("/{id}")
	public Orden obtenerPorId(@PathVariable (value = "id") Integer id) {
		return ordenRepositorio.findById(id).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Orden No Encontrada.");
		});
	}
	
	@PostMapping
	public Orden guardar(@Valid @RequestBody Orden orden) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
	    Date date = new Date();  
	    orden.setFechaHora(formatter.format(date)); 
		return ordenRepositorio.save(orden);
	}
	
	@PutMapping
	public Orden actualizar(@Valid @RequestBody Orden orden) {
		int id = orden.getId();
		if (ordenRepositorio.findById(id).isPresent()) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");  
		    Date date = new Date();  
		    orden.setFechaHora(formatter.format(date)); 
			return ordenRepositorio.save(orden);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Orden No Encontrada.");
		}
	}
	
	@DeleteMapping("/{id}")
	public void borrar(@PathVariable (value = "id") Integer id) {
		if (ordenRepositorio.findById(id).isPresent()) {
			ordenRepositorio.delete(ordenRepositorio.findById(id).get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Orden No Encontrada.");
		}
	}

}
