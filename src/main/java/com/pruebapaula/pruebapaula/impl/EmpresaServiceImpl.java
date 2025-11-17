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
        try {

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

        } catch (ResourceAlreadyExistsException e) {
            throw e; // permitir que fluya
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al crear la empresa.", e);
        }
    }

    @Override
    public EmpresaResponseDTO obtener(String nit) {
        try {

            Empresa empresa = empresaRepository.findById(nit)
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Empresa no encontrada con NIT: " + nit
                            )
                    );

            return toResponse(empresa);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al obtener la empresa.", e);
        }
    }

    @Override
    public EmpresaResponseDTO actualizar(String nit, EmpresaRequestDTO request) {
        try {

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

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al actualizar la empresa.", e);
        }
    }

    @Override
    public boolean eliminar(String nit) {
        try {

            if (!empresaRepository.existsById(nit)) {
                throw new ResourceNotFoundException(
                        "No existe empresa con NIT: " + nit
                );
            }

            empresaRepository.deleteById(nit);
            return true;

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al eliminar la empresa.", e);
        }
    }

    @Override
    public List<EmpresaResponseDTO> listar() {
        try {

            return empresaRepository.findAll().stream()
                    .map(this::toResponse)
                    .toList();

        } catch (Exception e) {
            throw new RuntimeException("Error inesperado al listar empresas.", e);
        }
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
