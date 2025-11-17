package com.pruebapaula.pruebapaula.auth.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PasswordEncoderBeanTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordEncoderDebeEncriptarYMatchear() {
        String raw = "Adm1nT3st";
        String hash = passwordEncoder.encode(raw);

        assertTrue(passwordEncoder.matches(raw, hash),
                "El hash generado debe coincidir con la contraseña original");
    }
}
