package com.pruebapaula.pruebapaula.auth;

import com.pruebapaula.pruebapaula.entities.Role;
import com.pruebapaula.pruebapaula.entities.Usuario;
import com.pruebapaula.pruebapaula.repository.RoleRepository;
import com.pruebapaula.pruebapaula.repository.UsuarioRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginFlowTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @Order(1)
    void loginDebeSerValidoConAdm1nT3st() {

        // 1. BORRAR SOLO EL USUARIO
        usuarioRepository.findByEmail("admin_test@prueba.com")
                .ifPresent(usuarioRepository::delete);

        // 2. CREAR O REUTILIZAR EL ROL ADMIN (NO BORRARLO)
        Role rol = roleRepository.findByNombre("ADMIN")
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setNombre("ADMIN");
                    return roleRepository.save(r);
                });

        // 3. CREAR USUARIO ADMIN TEST
        Usuario admin = new Usuario();
        admin.setEmail("admin_test@prueba.com");
        admin.setPassword(passwordEncoder.encode("admin123"));
        admin.setRole(rol);
        usuarioRepository.save(admin);

        // 4. VALIDAR LOGIN
        Usuario user = usuarioRepository.findByEmail("admin_test@prueba.com")
                .orElse(null);

        assertNotNull(user);
        assertTrue(passwordEncoder.matches("admin123", user.getPassword()));
    }
}
