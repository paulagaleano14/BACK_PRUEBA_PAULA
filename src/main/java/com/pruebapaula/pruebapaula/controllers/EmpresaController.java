package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaRequestDTO;
import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;
import com.pruebapaula.pruebapaula.dto.auth.ErrorResponseDTO;
import com.pruebapaula.pruebapaula.services.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
@CrossOrigin
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(empresaService.listar());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al listar empresas: " + ex.getMessage()));
        }
    }

    @GetMapping("/{nit}")
    public ResponseEntity<?> obtener(@PathVariable String nit) {
        try {
            EmpresaResponseDTO empresa = empresaService.obtener(nit);

            if (empresa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Empresa no encontrada"));
            }

            return ResponseEntity.ok(empresa);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al obtener empresa: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody EmpresaRequestDTO request) {
        try {
            EmpresaResponseDTO empresa = empresaService.crear(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresa);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al crear empresa: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{nit}")
    public ResponseEntity<?> actualizar(
            @PathVariable String nit,
            @Valid @RequestBody EmpresaRequestDTO request
    ) {
        try {
            EmpresaResponseDTO empresaActualizada = empresaService.actualizar(nit, request);

            if (empresaActualizada == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Empresa no encontrada"));
            }

            return ResponseEntity.ok(empresaActualizada);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al actualizar empresa: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{nit}")
    public ResponseEntity<?> eliminar(@PathVariable String nit) {
        try {
            boolean eliminado = empresaService.eliminar(nit);

            if (!eliminado) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Empresa no encontrada"));
            }

            return ResponseEntity.noContent().build();

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al eliminar empresa: " + ex.getMessage()));
        }
    }
}
