package com.pge.strategic.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * Serviço de detalhes do usuário para Spring Security
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String email) {
        // Implementação será completada quando o repositório de usuários estiver disponível
        // Por enquanto, retorna null para compilação
        return null;
    }
}
