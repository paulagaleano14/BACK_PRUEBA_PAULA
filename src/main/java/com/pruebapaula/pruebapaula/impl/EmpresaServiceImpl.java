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

        if (empresaRepository.existsByNit(request.getNit())) {
            throw new ResourceAlreadyExistsException("Ya existe una empresa con el NIT: " + request.getNit());
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
    public EmpresaResponseDTO actualizar(Long id, EmpresaRequestDTO request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa con id " + id + " no existe."));

        empresa.setNombre(request.getNombre());
        empresa.setDireccion(request.getDireccion());
        empresa.setTelefono(request.getTelefono());

        return toResponse(empresaRepository.save(empresa));
    }

    @Override
    public void eliminar(Long id) {
        if (!empresaRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe empresa con id " + id);
        }
        empresaRepository.deleteById(id);
    }

    @Override
    public EmpresaResponseDTO obtener(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa con id " + id + " no existe."));
        return toResponse(empresa);
    }

    @Override
    public List<EmpresaResponseDTO> listar() {
        return empresaRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private EmpresaResponseDTO toResponse(Empresa e) {
        return EmpresaResponseDTO.builder()
                .id(e.getId())
                .nit(e.getNit())
                .nombre(e.getNombre())
                .direccion(e.getDireccion())
                .telefono(e.getTelefono())
                .build();
    }
}
