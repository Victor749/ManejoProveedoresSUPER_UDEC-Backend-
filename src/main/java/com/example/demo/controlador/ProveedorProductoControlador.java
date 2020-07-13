package com.example.demo.controlador;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.modelo.ProveedorProducto;
import com.example.demo.modelo.ProveedorProductoId;
import com.example.demo.repositorio.ProveedorProductoRepositorio;
import com.example.demo.repositorio.ProveedorRepositorio;
import com.example.demo.repositorio.ProductoRepositorio;

@RestController
@RequestMapping ("/api/catalogos")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH, RequestMethod.DELETE})
public class ProveedorProductoControlador {

	@Autowired
	ProveedorProductoRepositorio proveedorProductoRepositorio;
	
	@Autowired
	ProveedorRepositorio proveedorRepositorio;
	
	@Autowired
	ProductoRepositorio productoRepositorio;
	
	@GetMapping
	public Iterable<ProveedorProducto> obtenerTodos() {
		return proveedorProductoRepositorio.findAll();
	}
	
	@GetMapping ("/proveedor/{rucProveedor}/producto/{codigoProducto}")
	public ProveedorProducto obtenerPorId(@PathVariable (value = "rucProveedor") String rucProveedor, @PathVariable (value = "codigoProducto") String codigoProducto) {
		ProveedorProductoId id = new ProveedorProductoId(rucProveedor, codigoProducto);
		return proveedorProductoRepositorio.findById(id).orElseGet(() -> {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProveedorProducto No Encontrado.");
		});
	}
	
	@GetMapping ("/proveedor/{rucProveedor}")
	public Iterable<ProveedorProducto> obtenerPorProveedor(@PathVariable (value = "rucProveedor") String rucProveedor) {
		return proveedorProductoRepositorio.encontrarProveedorProductoPorProveedor(rucProveedor);
	}
	
	@PostMapping ("/proveedor/{rucProveedor}/producto/{codigoProducto}")
	public ProveedorProducto guardar(@PathVariable (value = "rucProveedor") String rucProveedor, @PathVariable (value = "codigoProducto") String codigoProducto, @Valid @RequestBody ProveedorProducto proveedorProducto) {
		proveedorProducto.setProveedor(proveedorRepositorio.findById(rucProveedor).get());
		proveedorProducto.setProducto(productoRepositorio.findById(codigoProducto).get());
		proveedorProducto.setId();
		return proveedorProductoRepositorio.save(proveedorProducto);
	}
	
	@PatchMapping ("/proveedor/{rucProveedor}/producto/{codigoProducto}/costo/{costo}/tiempo/{tiempo}")
	public ProveedorProducto actualizar (@PathVariable (value = "rucProveedor") String rucProveedor, @PathVariable (value = "codigoProducto") String codigoProducto, @PathVariable (value = "costo") String costo, @PathVariable (value = "tiempo") String tiempo) {
		ProveedorProductoId id = new ProveedorProductoId(rucProveedor, codigoProducto);
		if (proveedorProductoRepositorio.findById(id).isPresent()) {
			ProveedorProducto proveedorProducto = proveedorProductoRepositorio.findById(id).get();
			if (costo.matches("[0-9]+[.]{1}+[0-9]{2}") && tiempo.matches("\\d+")) {
				proveedorProducto.setCostoUnitario(new BigDecimal(costo));
				proveedorProducto.setTiempoEnvio(Integer.parseInt(tiempo));
				return proveedorProductoRepositorio.save(proveedorProducto);
			} else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Argumentos Inválidos.");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProveedorProducto No Encontrado.");
		}		
	}
	
	@DeleteMapping ("/proveedor/{rucProveedor}/producto/{codigoProducto}")
	public void borrar(@PathVariable (value = "rucProveedor") String rucProveedor, @PathVariable (value = "codigoProducto") String codigoProducto) {
		ProveedorProductoId id = new ProveedorProductoId(rucProveedor, codigoProducto);
		if (proveedorProductoRepositorio.findById(id).isPresent()) {
			proveedorProductoRepositorio.delete(proveedorProductoRepositorio.findById(id).get());
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ProveedorProducto No Encontrado.");
		}
	}
	
}