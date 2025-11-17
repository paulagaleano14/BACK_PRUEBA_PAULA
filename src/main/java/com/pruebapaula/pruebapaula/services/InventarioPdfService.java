package com.pruebapaula.pruebapaula.services;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pruebapaula.pruebapaula.entities.Empresa;
import com.pruebapaula.pruebapaula.entities.Producto;
import com.pruebapaula.pruebapaula.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class InventarioPdfService {

    private final EmpresaRepository empresaRepository;

    public byte[] generarPDF(String nit) throws Exception {

        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document();
        PdfWriter.getInstance(doc, baos);

        doc.open();

        doc.add(new Paragraph("Inventario - " + empresa.getNombre(),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));

        doc.add(new Paragraph("NIT: " + empresa.getNit()));
        doc.add(new Paragraph(" ")); // espacio

        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        table.addCell("Código");
        table.addCell("Nombre");
        table.addCell("Características");
        table.addCell("Precios");

        for (Producto p : empresa.getProductos()) {
            table.addCell(p.getCodigo());
            table.addCell(p.getNombre());
            table.addCell(p.getCaracteristicas());
            table.addCell(
                    p.getPrecios().toString()
            );
        }

        doc.add(table);
        doc.close();

        return baos.toByteArray();
    }
}
