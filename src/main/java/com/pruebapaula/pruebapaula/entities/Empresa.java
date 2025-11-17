package com.pruebapaula.pruebapaula.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @JsonIgnore
    @OneToMany(mappedBy = "empresa")
    private List<Producto> productos;

}