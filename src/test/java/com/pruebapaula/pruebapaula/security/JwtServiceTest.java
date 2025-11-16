package com.pruebapaula.pruebapaula.security;

import com.pruebapaula.pruebapaula.entities.Role;
import com.pruebapaula.pruebapaula.entities.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void generarYValidarJwtDebeFuncionar() {

        assertNotNull(jwtService, "JwtService NO fue inyectado por Spring. Revisa si tiene @Service.");

        Usuario u = new Usuario();
        u.setEmail("test@prueba.com");

        Role r = new Role();
        r.setId(1L);
        r.setNombre("ADMIN");
        u.setRole(r);

        String token = jwtService.generateToken(u);

        assertNotNull(token, "El token no debe ser null");

        String username = jwtService.extractUsername(token);

        assertEquals("test@prueba.com", username);
        assertTrue(jwtService.isTokenValid(token, u.getEmail()));
    }
}
