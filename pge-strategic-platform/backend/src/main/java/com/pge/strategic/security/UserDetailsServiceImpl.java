package com.pge.strategic.security;

import com.pge.strategic.model.AppUser;
import com.pge.strategic.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

/**
 * Implementação do serviço de detalhes do usuário
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final AppUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = userRepository.findByEmail(email);
        
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado com e-mail: " + email);
        }

        AppUser user = userOptional.get();

        // Verifica se usuário está ativo
        if (!user.getIsActive()) {
            throw new UsernameNotFoundException("Usuário inativo: " + email);
        }

        // Cria authorities baseadas no nível de role
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleLevel().name());

        return User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(Collections.singletonList(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }

    /**
     * Carrega usuário por ID para validações internas
     */
    @Transactional(readOnly = true)
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        Optional<AppUser> userOptional = userRepository.findById(userId);
        
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado com ID: " + userId);
        }

        AppUser user = userOptional.get();

        if (!user.getIsActive()) {
            throw new UsernameNotFoundException("Usuário inativo: " + userId);
        }

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRoleLevel().name());

        return User.builder()
                .username(user.getEmail())
                .password(user.getPasswordHash())
                .authorities(Collections.singletonList(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
