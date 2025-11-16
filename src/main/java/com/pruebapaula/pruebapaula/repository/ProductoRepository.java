package com.pruebapaula.pruebapaula.repository;

import com.pruebapaula.pruebapaula.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByEmpresaNit(String nit);
}
