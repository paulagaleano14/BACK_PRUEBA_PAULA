package com.pruebapaula.pruebapaula.security;

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
    void beanPasswordEncoderDebeMatchearHashDeAdmin123() {
        String raw = "admin123";
        String hash = "$2a$10$hXyoKLx3GqCrUG8O0uK62eO1qJg9gE7MGvRZKVnM7k8vZm5BPEq1W";

        boolean matches = passwordEncoder.matches(raw, hash);

        System.out.println("PasswordEncoderBeanTest -> matches = " + matches);

        assertTrue(matches, "El bean PasswordEncoder debe reconocer el hash de admin123");
    }
}
