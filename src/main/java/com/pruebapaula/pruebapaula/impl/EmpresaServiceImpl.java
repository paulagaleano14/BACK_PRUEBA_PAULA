package com.pruebapaula.pruebapaula.impl;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaRequestDTO;
import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;
import com.pruebapaula.pruebapaula.entities.Empresa;
import com.pruebapaula.pruebapaula.exceptions.ResourceAlreadyExistsException;
import com.pruebapaula.pruebapaula.exceptions.ResourceNotFoundException;
import com.pruebapaula.pruebapaula.repository.EmpresaRepository;
import com.pruebapaula.pruebapaula.services.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {

    private final EmpresaRepository empresaRepository;

    @Override
    public EmpresaResponseDTO crear(EmpresaRequestDTO request) {

        if (empresaRepository.existsById(request.getNit())) {
            throw new ResourceAlreadyExistsException(
                    "La empresa con NIT " + request.getNit() + " ya existe."
            );
        }

        Empresa empresa = new Empresa();
        empresa.setNit(request.getNit());
        empresa.setNombre(request.getNombre());
        empresa.setDireccion(request.getDireccion());
        empresa.setTelefono(request.getTelefono());

        Empresa saved = empresaRepository.save(empresa);
        return toResponse(saved);
    }

    @Override
    public EmpresaResponseDTO obtener(String nit) {
        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Empresa no encontrada con NIT: " + nit
                        )
                );

        return toResponse(empresa);
    }

    @Override
    public EmpresaResponseDTO actualizar(String nit, EmpresaRequestDTO request) {
        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Empresa no encontrada con NIT: " + nit
                        )
                );

        empresa.setNombre(request.getNombre());
        empresa.setDireccion(request.getDireccion());
        empresa.setTelefono(request.getTelefono());

        Empresa updated = empresaRepository.save(empresa);
        return toResponse(updated);
    }

    @Override
    public boolean eliminar(String nit) {
        if (!empresaRepository.existsById(nit)) {
            throw new ResourceNotFoundException(
                    "No existe empresa con NIT: " + nit
            );
        }

        empresaRepository.deleteById(nit);
        return true;
    }

    @Override
    public List<EmpresaResponseDTO> listar() {
        return empresaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private EmpresaResponseDTO toResponse(Empresa e) {
        return EmpresaResponseDTO.builder()
                .nit(e.getNit())
                .nombre(e.getNombre())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .build();
    }
}
