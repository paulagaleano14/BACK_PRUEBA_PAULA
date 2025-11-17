package com.pruebapaula.pruebapaula.dto.inventario;

import lombok.Data;

@Data
public class InventarioEmailRequest {
    private String empresaNIT;
    private String emailDestino;
}