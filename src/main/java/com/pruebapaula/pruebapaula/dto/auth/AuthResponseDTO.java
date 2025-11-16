package com.pruebapaula.pruebapaula.dto.auth;

import com.pruebapaula.pruebapaula.entities.Role;
import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.ses.endpoints.internal.Value;

@Data
@Builder
public class AuthResponseDTO {
    private String token;
    private String role;
}

