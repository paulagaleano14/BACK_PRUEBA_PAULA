package com.pruebapaula.pruebapaula.dto.empresa;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmpresaResponseDTO {

    private Long id;
    private String nit;
    private String nombre;
    private String direccion;
    private String telefono;
}

