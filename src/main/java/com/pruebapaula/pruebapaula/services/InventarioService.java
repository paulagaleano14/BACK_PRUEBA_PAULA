package com.pruebapaula.pruebapaula.services;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.pruebapaula.pruebapaula.entities.Empresa;
import com.pruebapaula.pruebapaula.entities.Producto;
import com.pruebapaula.pruebapaula.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final EmpresaRepository empresaRepository;

    public ByteArrayInputStream generarInventarioPDF(String nit) throws Exception {

        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        Document doc = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(doc, out);
        doc.open();

        // TÍTULO
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
        Paragraph titulo = new Paragraph("Inventario - " + empresa.getNombre(), titleFont);
        titulo.setAlignment(Element.ALIGN_CENTER);
        titulo.setSpacingAfter(20);
        doc.add(titulo);

        // TABLA
        PdfPTable table = new PdfPTable(4); // Columnas
        table.setWidthPercentage(100);

        agregarCelda(table, "Código", true);
        agregarCelda(table, "Nombre", true);
        agregarCelda(table, "Características", true);
        agregarCelda(table, "Precios", true);

        for (Producto p : empresa.getProductos()) {
            agregarCelda(table, p.getCodigo(), false);
            agregarCelda(table, p.getNombre(), false);
            agregarCelda(table, p.getCaracteristicas(), false);

            String precios = p.getPrecios().stream()
                    .map(px -> px.getMoneda() + ": " + px.getValor())
                    .reduce("", (a, b) -> a + " | " + b);

            agregarCelda(table, precios, false);
        }

        doc.add(table);
        doc.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private void agregarCelda(PdfPTable table, String texto, boolean header) {
        PdfPCell cell = new PdfPCell(new Phrase(texto));
        if (header) {
            cell.setBackgroundColor(new Color(200, 200, 200));
            cell.setPadding(5);
        }
        table.addCell(cell);
    }
}
