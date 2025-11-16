package com.pruebapaula.pruebapaula.services;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaRequestDTO;
import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;

import java.util.List;

public interface EmpresaService {

    EmpresaResponseDTO crear(EmpresaRequestDTO request);

    EmpresaResponseDTO actualizar(Long id, EmpresaRequestDTO request);

    void eliminar(Long id);

    EmpresaResponseDTO obtener(Long id);

    List<EmpresaResponseDTO> listar();
}
