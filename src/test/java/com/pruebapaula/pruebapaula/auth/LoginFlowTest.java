package com.pruebapaula.pruebapaula.auth;

import com.pruebapaula.pruebapaula.entities.Role;
import com.pruebapaula.pruebapaula.entities.Usuario;
import com.pruebapaula.pruebapaula.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LoginFlowTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void loginDebeSerValidoConAdmin123() {
        // 1. Crear usuario como lo haría el inicializador
        Role rol = new Role();
        rol.setId(1L);
        rol.setNombre("ADMIN");
        Usuario admin = new Usuario();
        admin.setEmail("admin_test@prueba.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(rol);
        usuarioRepository.save(admin);

        // 2. Simular login: buscar por email
        Usuario user = usuarioRepository.findByEmail("admin_test@prueba.com")
                .orElse(null);

        assertNotNull(user, "Usuario deberia existir");

        // 3. Comparar password como hace el AuthController
        boolean matches = passwordEncoder.matches("admin123", user.getPassword());

        System.out.println("LoginFlowTest -> matches = " + matches);

        assertTrue(matches, "La contraseña admin123 debe coincidir con el hash guardado");
    }
}

