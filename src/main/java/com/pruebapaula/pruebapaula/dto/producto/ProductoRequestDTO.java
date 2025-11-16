package com.pruebapaula.pruebapaula.dto.producto;

import lombok.Data;
import java.util.Map;

@Data
public class ProductoRequestDTO {
    private String codigo;
    private String nombre;
    private String caracteristicas;
    private String empresaNIT;
    private Map<String, Double> precios;
}
