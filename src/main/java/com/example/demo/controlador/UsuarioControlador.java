package com.example.demo.controlador;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modelo.LoginResult;
import com.example.demo.modelo.Usuario;
import com.example.demo.repositorio.UsuarioRepositorio;

@RestController
@RequestMapping ("/api/usuarios")
@CrossOrigin (origins = "*", methods = {RequestMethod.GET})
public class UsuarioControlador {
	
	@Autowired
	UsuarioRepositorio usuarioRepositorio;
	
	@GetMapping ("/username/{username}/password/{password}")
	public LoginResult login(@PathVariable (value = "username") String username, @PathVariable (value = "password") String password) {
		Usuario usuarioLogin = new Usuario();
		usuarioLogin.setUsername(username);
		usuarioLogin.setPassword(password);
		LoginResult loginResult = new LoginResult();
		loginResult.setOk(false);
		Optional<Usuario> usuarioReal = usuarioRepositorio.findById(usuarioLogin.getUsername());
		if (usuarioReal.isPresent()) { 
			if (usuarioReal.get().getPassword().contentEquals(usuarioLogin.getPassword())) {
				loginResult.setOk(true);
			}
		}
		return loginResult;
	}

}
