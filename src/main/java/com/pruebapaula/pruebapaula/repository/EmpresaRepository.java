package com.pruebapaula.pruebapaula.repository;

import com.pruebapaula.pruebapaula.entities.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
    boolean existsByNit(String nit);
}
