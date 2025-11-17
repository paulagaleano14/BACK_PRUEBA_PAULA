package com.pruebapaula.pruebapaula.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void enviarInventario(String destino, byte[] pdf, String empresaNIT) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper =
                new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(destino);
        helper.setSubject("Inventario PDF - Empresa " + empresaNIT);
        helper.setText("Adjunto encontrarás el inventario solicitado.", false);

        helper.addAttachment(
                "inventario_" + empresaNIT + ".pdf",
                new ByteArrayResource(pdf)
        );

        mailSender.send(message);
    }
}
