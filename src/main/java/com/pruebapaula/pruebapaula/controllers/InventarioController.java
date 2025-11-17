package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.inventario.InventarioEmailRequest;
import com.pruebapaula.pruebapaula.dto.inventario.InventarioItemDTO;
import com.pruebapaula.pruebapaula.dto.auth.ErrorResponseDTO;
import com.pruebapaula.pruebapaula.services.InventarioService;
import com.pruebapaula.pruebapaula.services.PdfService;
import com.pruebapaula.pruebapaula.services.EmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventario")
@CrossOrigin
public class InventarioController {

    private final InventarioService inventarioService;
    private final PdfService pdfService;
    private final EmailService emailService;

    @GetMapping("/{nit}")
    public ResponseEntity<?> obtenerInventario(@PathVariable String nit) {
        try {
            List<InventarioItemDTO> inventario = inventarioService.listarInventario(nit);

            if (inventario == null || inventario.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("No se encontró inventario para la empresa con NIT: " + nit));
            }

            return ResponseEntity.ok(inventario);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al obtener inventario: " + ex.getMessage()));
        }
    }

    @GetMapping("/{nit}/pdf")
    public ResponseEntity<?> obtenerPDF(@PathVariable String nit) {
        try {
            byte[] pdf = pdfService.generarPDF(nit);

            if (pdf == null || pdf.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("No se pudo generar el PDF del inventario para el NIT: " + nit));
            }

            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=inventario.pdf")
                    .body(pdf);

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al generar PDF: " + ex.getMessage()));
        }
    }

    @PostMapping("/email")
    public ResponseEntity<?> enviarPDFPorEmail(@RequestBody InventarioEmailRequest req) {
        try {
            byte[] pdf = pdfService.generarPDF(req.getEmpresaNIT());

            if (pdf == null || pdf.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("No se pudo generar el PDF para enviar por correo"));
            }

            emailService.enviarInventario(req.getEmailDestino(), pdf, req.getEmpresaNIT());

            return ResponseEntity.ok("PDF enviado por correo!");

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Error al enviar PDF por correo: " + ex.getMessage()));
        }
    }

}
