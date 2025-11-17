package com.pruebapaula.pruebapaula.controllers;

import com.pruebapaula.pruebapaula.dto.auth.AuthRequestDTO;
import com.pruebapaula.pruebapaula.dto.auth.AuthResponseDTO;
import com.pruebapaula.pruebapaula.dto.auth.ErrorResponseDTO;
import com.pruebapaula.pruebapaula.security.JwtService;
import com.pruebapaula.pruebapaula.entities.Usuario;
import com.pruebapaula.pruebapaula.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {

        try {
            // Buscar usuario
            Usuario user = usuarioRepository.findByEmail(request.getEmail())
                    .orElse(null);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponseDTO("Usuario no encontrado"));
            }

            // Validar contraseña
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new ErrorResponseDTO("Usuario o contraseña incorrectos"));
            }

            // Generar token
            String token = jwtService.generateToken(user);

            return ResponseEntity.ok(
                    AuthResponseDTO.builder()
                            .token(token)
                            .role(user.getRole().getId())
                            .build()
            );

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponseDTO("Ocurrió un error interno: " + ex.getMessage()));
        }
    }
}
