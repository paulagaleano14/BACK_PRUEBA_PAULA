package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.inventario.InventarioEmailRequest;
import com.pruebapaula.pruebapaula.dto.inventario.InventarioItemDTO;
import com.pruebapaula.pruebapaula.services.InventarioService;
import com.pruebapaula.pruebapaula.services.PdfService;
import com.pruebapaula.pruebapaula.services.EmailService;

import lombok.RequiredArgsConstructor;
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
    public List<InventarioItemDTO> obtenerInventario(@PathVariable String nit) {
        return inventarioService.listarInventario(nit);
    }

    @GetMapping("/{nit}/pdf")
    public ResponseEntity<byte[]> obtenerPDF(@PathVariable String nit) throws Exception {
        byte[] pdf = pdfService.generarPDF(nit);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=inventario.pdf")
                .body(pdf);
    }

    @PostMapping("/email")
    public ResponseEntity<String> enviarPDFPorEmail(@RequestBody InventarioEmailRequest req) throws Exception {

        byte[] pdf = pdfService.generarPDF(req.getEmpresaNIT());

        emailService.enviarInventario(req.getEmailDestino(), pdf, req.getEmpresaNIT());

        return ResponseEntity.ok("PDF enviado por correo!");
    }

}
