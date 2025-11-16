package com.pruebapaula.pruebapaula.security;

import com.pruebapaula.pruebapaula.repository.UsuarioRepository;
import com.pruebapaula.pruebapaula.entities.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String roleName = user.getRole().getNombre(); // ROLE_ADMIN o ROLE_EXTERNO

        if (roleName.startsWith("ROLE_")) {
            roleName = roleName.substring(5); // ADMIN o EXTERNO
        }

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(roleName) // Spring agregará ROLE_ automáticamente
                .build();
    }
}

