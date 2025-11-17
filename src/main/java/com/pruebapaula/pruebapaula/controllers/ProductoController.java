package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.producto.ProductoRequestDTO;
import com.pruebapaula.pruebapaula.dto.producto.ProductoResponseDTO;
import com.pruebapaula.pruebapaula.dto.auth.ErrorResponseDTO;
import com.pruebapaula.pruebapaula.services.ProductoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> listar() {
        try {
            List<ProductoResponseDTO> productos = productoService.listar();

            if (productos == null || productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("No hay productos registrados"));
            }

            return ResponseEntity.ok(productos);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al listar productos: " + ex.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProductoRequestDTO dto) {
        try {
            ProductoResponseDTO creado = productoService.crearProducto(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al crear producto: " + ex.getMessage()));
        }
    }

    @GetMapping("/empresa/{nit}")
    public ResponseEntity<?> listarPorEmpresa(@PathVariable String nit) {
        try {
            List<ProductoResponseDTO> productos = productoService.listarProductosPorEmpresa(nit);

            if (productos == null || productos.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("No se encontraron productos para la empresa con NIT: " + nit));
            }

            return ResponseEntity.ok(productos);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al obtener productos por empresa: " + ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable Long id, @RequestBody ProductoRequestDTO dto) {
        try {
            ProductoResponseDTO editado = productoService.editarProducto(id, dto);

            if (editado == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Producto no encontrado con ID: " + id));
            }

            return ResponseEntity.ok(editado);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al editar producto: " + ex.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            boolean eliminado = productoService.eliminarProducto(id);

            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Producto no encontrado con ID: " + id));
            }

            return ResponseEntity.noContent().build();

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al eliminar producto: " + ex.getMessage()));
        }
    }

}
