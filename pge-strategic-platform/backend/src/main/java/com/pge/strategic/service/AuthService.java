package com.pge.strategic.service;

import com.pge.strategic.model.AppUser;
import com.pge.strategic.repository.AppUserRepository;
import com.pge.strategic.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Serviço de autenticação e autorização
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

    /**
     * Realiza autenticação do usuário e gera tokens JWT
     */
    @Transactional
    public AuthResponse authenticate(String email, String password) {
        try {
            // Autentica credenciais
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            // Busca usuário no banco
            Optional<AppUser> userOptional = userRepository.findByEmail(email);
            
            if (userOptional.isEmpty()) {
                throw new BadCredentialsException("Credenciais inválidas");
            }

            AppUser user = userOptional.get();

            // Verifica se usuário está ativo
            if (!user.getIsActive()) {
                throw new BadCredentialsException("Usuário inativo. Contate o administrador.");
            }

            // Carrega UserDetails
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // Gera tokens
            String accessToken = tokenProvider.generateToken(
                userDetails, 
                user.getRoleLevel().name(), 
                user.getId()
            );
            String refreshToken = tokenProvider.generateRefreshToken(userDetails);

            // Atualiza último login
            userRepository.updateLastLogin(user.getId(), LocalDateTime.now());

            log.info("Usuário autenticado com sucesso: {}", email);

            return new AuthResponse(
                accessToken,
                refreshToken,
                86400000L, // 24 horas em milissegundos
                user.getEmail(),
                user.getFullName(),
                user.getRoleLevel().name(),
                user.getId()
            );

        } catch (BadCredentialsException ex) {
            log.warn("Falha na autenticação para usuário: {}", email);
            throw ex;
        } catch (Exception ex) {
            log.error("Erro durante autenticação: {}", ex.getMessage(), ex);
            throw new RuntimeException("Erro interno durante autenticação");
        }
    }

    /**
     * Renova token de acesso usando refresh token
     */
    @Transactional(readOnly = true)
    public AuthResponse refreshToken(String refreshToken) {
        try {
            String email = tokenProvider.getUsernameFromToken(refreshToken);
            
            Optional<AppUser> userOptional = userRepository.findByEmail(email);
            
            if (userOptional.isEmpty() || !userOptional.get().getIsActive()) {
                throw new BadCredentialsException("Usuário não encontrado ou inativo");
            }

            AppUser user = userOptional.get();
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (!tokenProvider.validateToken(refreshToken, userDetails)) {
                throw new BadCredentialsException("Refresh token inválido ou expirado");
            }

            // Gera novo access token
            String newAccessToken = tokenProvider.generateToken(
                userDetails,
                user.getRoleLevel().name(),
                user.getId()
            );

            // Gera novo refresh token
            String newRefreshToken = tokenProvider.generateRefreshToken(userDetails);

            log.info("Token renovado com sucesso para usuário: {}", email);

            return new AuthResponse(
                newAccessToken,
                newRefreshToken,
                86400000L,
                user.getEmail(),
                user.getFullName(),
                user.getRoleLevel().name(),
                user.getId()
            );

        } catch (Exception ex) {
            log.error("Erro ao renovar token: {}", ex.getMessage(), ex);
            throw new BadCredentialsException("Não foi possível renovar o token");
        }
    }

    /**
     * Registra novo usuário no sistema
     */
    @Transactional
    public AppUser registerUser(AppUser newUser, String password) {
        // Verifica se e-mail já existe
        if (userRepository.existsByEmail(newUser.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado no sistema");
        }

        // Criptografa senha
        newUser.setPasswordHash(passwordEncoder.encode(password));
        newUser.setIsActive(true);

        // Salva usuário
        AppUser savedUser = userRepository.save(newUser);
        log.info("Novo usuário registrado: {}", savedUser.getEmail());

        return savedUser;
    }

    /**
     * Altera senha do usuário
     */
    @Transactional
    public void changePassword(Long userId, String oldPassword, String newPassword) {
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Verifica senha atual
        if (!passwordEncoder.matches(oldPassword, user.getPasswordHash())) {
            throw new BadCredentialsException("Senha atual incorreta");
        }

        // Atualiza senha
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        log.info("Senha alterada com sucesso para usuário ID: {}", userId);
    }

    /**
     * Desativa usuário (soft delete)
     */
    @Transactional
    public void deactivateUser(Long userId) {
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setIsActive(false);
        userRepository.save(user);

        log.info("Usuário desativado: {}", user.getEmail());
    }

    /**
     * Reativa usuário
     */
    @Transactional
    public void reactivateUser(Long userId) {
        AppUser user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setIsActive(true);
        userRepository.save(user);

        log.info("Usuário reativado: {}", user.getEmail());
    }
}
