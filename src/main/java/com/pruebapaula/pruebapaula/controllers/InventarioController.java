package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.inventario.InventarioEmailRequest;
import com.pruebapaula.pruebapaula.services.EmailService;
import com.pruebapaula.pruebapaula.services.InventarioPdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioPdfService pdfService;
    private final EmailService emailService;

    @GetMapping("/pdf/{nit}")
    public ResponseEntity<byte[]> descargarPDF(@PathVariable String nit) throws Exception {

        byte[] pdf = pdfService.generarPDF(nit);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario_" + nit + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping("/email")
    public ResponseEntity<String> enviarPDFPorEmail(@RequestBody InventarioEmailRequest req) throws Exception {
        byte[] pdf = pdfService.generarPDF(req.getEmpresaNIT());
        emailService.enviarInventario(
                req.getEmailDestino(),
                pdf,
                req.getEmpresaNIT()
        );
        return ResponseEntity.ok("PDF enviado por correo!");
    }
}
