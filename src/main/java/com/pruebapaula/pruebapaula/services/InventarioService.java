package com.pruebapaula.pruebapaula.services;

import com.pruebapaula.pruebapaula.dto.inventario.InventarioItemDTO;
import com.pruebapaula.pruebapaula.entities.Empresa;
import com.pruebapaula.pruebapaula.entities.Producto;
import com.pruebapaula.pruebapaula.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final EmpresaRepository empresaRepository;

    public List<InventarioItemDTO> listarInventario(String nit) {

        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        return empresa.getProductos().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private InventarioItemDTO toDTO(Producto p) {
        InventarioItemDTO dto = new InventarioItemDTO();

        dto.setId(p.getId());
        dto.setCodigo(p.getCodigo());
        dto.setNombre(p.getNombre());
        dto.setCaracteristicas(p.getCaracteristicas());

        Map<String, Double> precios = new HashMap<>();
        p.getPrecios().forEach(px -> precios.put(px.getMoneda(), px.getValor()));
        dto.setPrecios(precios);

        return dto;
    }
}
