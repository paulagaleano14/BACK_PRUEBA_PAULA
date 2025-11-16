package com.pruebapaula.pruebapaula.repository;

import com.pruebapaula.pruebapaula.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    boolean existsById(Long id);
    boolean existsByNit(String nit);
}
