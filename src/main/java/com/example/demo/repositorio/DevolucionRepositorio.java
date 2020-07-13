package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Devolucion;

public interface DevolucionRepositorio extends JpaRepository<Devolucion, Integer> {

}
