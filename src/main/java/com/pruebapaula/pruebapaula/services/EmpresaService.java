package com.pruebapaula.pruebapaula.services;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaRequestDTO;
import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;

import java.util.List;

public interface EmpresaService {

    EmpresaResponseDTO crear(EmpresaRequestDTO request);

    EmpresaResponseDTO actualizar(String nit, EmpresaRequestDTO request);

    boolean eliminar(String nit);

    EmpresaResponseDTO obtener(String nit);

    List<EmpresaResponseDTO> listar();
}
