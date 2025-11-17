package com.pruebapaula.pruebapaula.dto.inventario;

import lombok.Data;

@Data
public class InventarioEmailRequest {
    private String emailDestino;
    private String empresaNIT;
}