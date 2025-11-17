package com.pruebapaula.pruebapaula.dto.inventario;

import lombok.Data;
import java.util.Map;

@Data
public class InventarioItemDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String caracteristicas;
    private Map<String, Double> precios;
}
