package pruebapaula.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BCryptRawTest {

    @Test
    void encodeYMatchesConAdmin123DebeFuncionar() {
        String raw = "admin123";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(raw);

        boolean matches = encoder.matches(raw, hash);

        System.out.println("BCryptRawTest -> hash generado = " + hash);
        System.out.println("BCryptRawTest -> matches = " + matches);

        assertTrue(matches, "El hash generado debe coincidir con admin123");
    }

    @Test
    void otraClaveNoDebeMatchear() {
        String raw = "admin123";

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(raw);

        boolean matches = encoder.matches("otraClave", hash);

        assertFalse(matches, "otraClave no debería coincidir con el hash de admin123");
    }
}
