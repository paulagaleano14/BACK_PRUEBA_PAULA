package com.pruebapaula.pruebapaula.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String direccion;
    private String telefono;

    @OneToMany(mappedBy = "cliente")
    private List<Orden> ordenes;
}
