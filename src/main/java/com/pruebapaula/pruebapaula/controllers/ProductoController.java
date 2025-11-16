package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;
import com.pruebapaula.pruebapaula.dto.producto.ProductoRequestDTO;
import com.pruebapaula.pruebapaula.dto.producto.ProductoResponseDTO;
import com.pruebapaula.pruebapaula.services.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoResponseDTO>> listar() {
        return ResponseEntity.ok(productoService.listar());
    }

    @PostMapping
    public ProductoResponseDTO crear(@RequestBody ProductoRequestDTO dto) {
        return productoService.crearProducto(dto);
    }

    @GetMapping("/empresa/{nit}")
    public List<ProductoResponseDTO> listar(@PathVariable String nit) {
        return productoService.listarProductosPorEmpresa(nit);
    }
}
