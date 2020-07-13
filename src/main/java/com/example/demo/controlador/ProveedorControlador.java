package com.example.demo.controlador;

import java.math.BigDecimal;

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

import com.example.demo.modelo.Proveedor;
import com.example.demo.repositorio.ProveedorRepositorio;

@RestController
@RequestMapping ("/api/proveedores")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE})
public class ProveedorControlador {

	@Autowired
	ProveedorRepositorio proveedorRepositorio;
	
	@GetMapping
	public Iterable<Proveedor> obtenerTodos() {
		return proveedorRepositorio.findAll();
	}
	
	@GetMapping ("/{ruc}")
	public Proveedor obtenerPorId(@PathVariable (value = "ruc") String ruc) {
		return proveedorRepositorio.findById(ruc).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor No Encontrado.");
		});
	}
	
	@PostMapping
	public Proveedor guardar(@Valid @RequestBody Proveedor proveedor) {
		proveedor.setSaldo(new BigDecimal("0.00"));
		return proveedorRepositorio.save(proveedor);
	}
	
	@PutMapping
	public Proveedor actualizar(@Valid @RequestBody Proveedor proveedor) {
		String id = proveedor.getRuc();
		if (proveedorRepositorio.findById(id).isPresent()) {
			BigDecimal saldo = proveedorRepositorio.findById(id).get().getSaldo();
			proveedor.setSaldo(saldo);
			return proveedorRepositorio.save(proveedor);
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor No Encontrado.");
		}
	}
	
	@DeleteMapping("/{ruc}")
	public void borrar(@PathVariable (value = "ruc") String ruc) {
		if (proveedorRepositorio.findById(ruc).isPresent()) {
			proveedorRepositorio.delete(proveedorRepositorio.findById(ruc).get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Proveedor No Encontrado.");
		}
	}
	
}
