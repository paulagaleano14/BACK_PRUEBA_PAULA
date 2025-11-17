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
import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final EmpresaRepository empresaRepository;

    public byte[] generarPDF(String nit) throws Exception {

        Empresa empresa = empresaRepository.findById(nit)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, baos);
        document.open();

        // --- TÍTULO ---
        Font titleFont = new Font(Font.HELVETICA, 20, Font.BOLD);
        Paragraph title = new Paragraph("Inventario - " + empresa.getNombre(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph("NIT: " + empresa.getNit()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        // --- TABLA ---
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);

        addHeaderCell(table, "Código");
        addHeaderCell(table, "Nombre");
        addHeaderCell(table, "Características");
        addHeaderCell(table, "Precios (COP / USD / EUR)");

        for (Producto p : empresa.getProductos()) {

            table.addCell(p.getCodigo());
            table.addCell(p.getNombre());
            table.addCell(p.getCaracteristicas());

            StringBuilder precios = new StringBuilder();
            p.getPrecios().forEach(px ->
                    precios.append(px.getMoneda()).append(": ").append(px.getValor()).append("  ")
            );

            table.addCell(precios.toString());
        }

        document.add(table);
        document.close();

        return baos.toByteArray();
    }

    private void addHeaderCell(PdfPTable table, String text) {
        Font font = new Font(Font.HELVETICA, 12, Font.BOLD);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(Color.lightGray);
        table.addCell(cell);
    }
}
