package com.pruebapaula.pruebapaula.repository;

import com.pruebapaula.pruebapaula.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByCodigo(String codigo);
}
