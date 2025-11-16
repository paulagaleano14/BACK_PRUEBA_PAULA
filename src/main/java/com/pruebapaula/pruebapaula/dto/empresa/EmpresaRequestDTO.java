package com.pruebapaula.pruebapaula.dto.empresa;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmpresaRequestDTO {

    @NotBlank
    private String nit;
    @NotBlank
    private String nombre;
    private String direccion;
    private String telefono;
}

