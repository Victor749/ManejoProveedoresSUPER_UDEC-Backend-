package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Factura;

public interface FacturaRepositorio extends JpaRepository<Factura, Integer> {

}
