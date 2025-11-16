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

        Usuario user = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDTO("Usuario o contraseña incorrectos"));
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(
                AuthResponseDTO.builder()
                        .token(token)
                        .role(user.getRole().getNombre())
                        .build()
        );
    }
}
