package com.example.demo.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modelo.Orden;

public interface OrdenRepositorio extends JpaRepository<Orden, Integer> {

}
