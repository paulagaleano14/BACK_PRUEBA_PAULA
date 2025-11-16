package com.pruebapaula.pruebapaula.impl;

import com.pruebapaula.pruebapaula.dto.producto.ProductoRequestDTO;
import com.pruebapaula.pruebapaula.dto.producto.ProductoResponseDTO;
import com.pruebapaula.pruebapaula.entities.*;
import com.pruebapaula.pruebapaula.exceptions.ResourceAlreadyExistsException;
import com.pruebapaula.pruebapaula.exceptions.ResourceNotFoundException;
import com.pruebapaula.pruebapaula.repository.*;
import com.pruebapaula.pruebapaula.services.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final EmpresaRepository empresaRepository;

    @Override
    public ProductoResponseDTO crearProducto(ProductoRequestDTO dto) {

        if (productoRepository.existsByCodigo(dto.getCodigo())) {
            throw new ResourceAlreadyExistsException("El producto ya existe");
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaNIT())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));

        Producto producto = Producto.builder()
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .caracteristicas(dto.getCaracteristicas())
                .empresa(empresa)
                .build();

        // Manejo de precios
        List<PrecioProducto> precios = new ArrayList<>();
        dto.getPrecios().forEach((moneda, valor) -> {
            precios.add(
                    PrecioProducto.builder()
                            .moneda(moneda)
                            .valor(valor)
                            .producto(producto)
                            .build()
            );
        });

        producto.setPrecios(precios);

        productoRepository.save(producto);

        return mapToResponse(producto);
    }

    @Override
    public List<ProductoResponseDTO> listarProductosPorEmpresa(String nit) {

        return productoRepository.findAll().stream()
                .filter(p -> p.getEmpresa().getNit().equals(nit))
                .map(this::mapToResponse)
                .toList();
    }

    private ProductoResponseDTO mapToResponse(Producto p) {
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(p.getId());
        dto.setCodigo(p.getCodigo());
        dto.setNombre(p.getNombre());
        dto.setCaracteristicas(p.getCaracteristicas());
        dto.setEmpresaNIT(p.getEmpresa().getNit());

        Map<String, Double> precios = new HashMap<>();
        p.getPrecios().forEach(px -> precios.put(px.getMoneda(), px.getValor()));

        dto.setPrecios(precios);

        return dto;
    }

    @Override
    public List<ProductoResponseDTO> listar() {
        return productoRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductoResponseDTO toResponse(Producto p) {
        Map<String, Double> preciosMap = p.getPrecios().stream()
                .collect(Collectors.toMap(
                        PrecioProducto::getMoneda,
                        PrecioProducto::getValor
                ));

        return ProductoResponseDTO.builder()
                .id(p.getId())
                .codigo(p.getCodigo())
                .nombre(p.getNombre())
                .caracteristicas(p.getCaracteristicas())
                .precios(preciosMap)
                .empresaNIT(p.getEmpresa().getNit())
                .nombre(p.getEmpresa().getNombre())
                .build();
    }


}
