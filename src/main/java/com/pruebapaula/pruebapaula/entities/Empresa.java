package com.pruebapaula.pruebapaula.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresa")
@Data
public class Empresa {

    @Id
    @Column(name = "nit", nullable = false, unique = true)
    private String nit;

    @Column(nullable = false)
    private String nombre;

    private String direccion;
    private String telefono;
}