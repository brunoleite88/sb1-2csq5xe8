package com.pge.strategic.security;

import lombok.Data;

/**
 * DTO para requisição de login
 */
@Data
public class LoginRequest {
    
    private String email;
    private String password;
}
