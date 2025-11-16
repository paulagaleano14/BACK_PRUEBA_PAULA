package com.pruebapaula.pruebapaula.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "precio_producto")
public class PrecioProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String moneda; // "COP", "USD", "EUR"
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    public PrecioProducto(String moneda, Double valor, Producto producto) {
        this.moneda = moneda;
        this.valor = valor;
        this.producto = producto;
    }
}
