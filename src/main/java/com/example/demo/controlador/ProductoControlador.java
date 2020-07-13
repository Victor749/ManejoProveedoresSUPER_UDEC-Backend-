package com.example.demo.controlador;

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

import com.example.demo.modelo.Producto;
import com.example.demo.repositorio.ProductoRepositorio;

@RestController
@RequestMapping ("/api/productos")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ProductoControlador {

	@Autowired
	ProductoRepositorio productoRepositorio;
	
	@GetMapping
	public Iterable<Producto> obtenerTodos() {
		return productoRepositorio.findAll();
	}
	
	@GetMapping ("/{codigo}")
	public Producto obtenerPorId(@PathVariable (value = "codigo") String codigo) {
		return productoRepositorio.findById(codigo).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto No Encontrado.");
		});
	}
	
	@PostMapping
	public Producto guardar(@Valid @RequestBody Producto producto) {
		return productoRepositorio.save(producto);
	}
	
	
	@PutMapping
	public Producto actualizar(@Valid @RequestBody Producto producto) {
		String id = producto.getCodigo();
		if (productoRepositorio.findById(id).isPresent()) {
			return productoRepositorio.save(producto);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto No Encontrado.");
		}
	}
	
	@DeleteMapping("/{codigo}")
	public void borrar(@PathVariable (value = "codigo") String codigo) {
		if (productoRepositorio.findById(codigo).isPresent()) {
			productoRepositorio.delete(productoRepositorio.findById(codigo).get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Producto No Encontrado.");
		}
	}
	
}