package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.empresa.EmpresaRequestDTO;
import com.pruebapaula.pruebapaula.dto.empresa.EmpresaResponseDTO;
import com.pruebapaula.pruebapaula.services.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<List<EmpresaResponseDTO>> listar() {
        return ResponseEntity.ok(empresaService.listar());
    }

    @GetMapping("/{nit}")
    public ResponseEntity<EmpresaResponseDTO> obtener(@PathVariable String nit) {
        return ResponseEntity.ok(empresaService.obtener(nit));
    }

    // Solo ADMIN puede crear/editar/eliminar
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> crear(
            @Valid @RequestBody EmpresaRequestDTO request
    ) {
        return ResponseEntity.ok(empresaService.crear(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{nit}")
    public ResponseEntity<EmpresaResponseDTO> actualizar(
            @PathVariable String nit,
            @Valid @RequestBody EmpresaRequestDTO request
    ) {
        return ResponseEntity.ok(empresaService.actualizar(nit, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{nit}")
    public ResponseEntity<Void> eliminar(@PathVariable String nit) {
        empresaService.eliminar(nit);
        return ResponseEntity.noContent().build();
    }
}
