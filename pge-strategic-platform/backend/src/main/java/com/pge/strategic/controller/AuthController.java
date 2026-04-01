package com.pge.strategic.controller;

import com.pge.strategic.model.AppUser;
import com.pge.strategic.security.AuthResponse;
import com.pge.strategic.security.LoginRequest;
import com.pge.strategic.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller de autenticação e autorização
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    /**
     * Realiza login do usuário
     * 
     * POST /api/auth/login
     * Body: { "email": "user@example.com", "password": "senha" }
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("Tentativa de login para usuário: {}", loginRequest.getEmail());
        
        AuthResponse response = authService.authenticate(
            loginRequest.getEmail(), 
            loginRequest.getPassword()
        );
        
        return ResponseEntity.ok(response);
    }

    /**
     * Renova token de acesso
     * 
     * POST /api/auth/refresh
     * Header: Authorization: Bearer <refresh_token>
     */
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        // Remove prefixo "Bearer " se existir
        String token = refreshToken.startsWith("Bearer ") 
            ? refreshToken.substring(7) 
            : refreshToken;
        
        AuthResponse response = authService.refreshToken(token);
        return ResponseEntity.ok(response);
    }

    /**
     * Registra novo usuário (apenas ADMIN)
     * 
     * POST /api/auth/register
     * Body: dados do usuário + senha
     */
    @PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AppUser> registerUser(
            @Valid @RequestBody RegisterUserRequest request) {
        
        AppUser newUser = AppUser.builder()
            .email(request.getEmail())
            .fullName(request.getFullName())
            .registrationNumber(request.getRegistrationNumber())
            .organUnitId(request.getOrganUnitId())
            .roleLevel(request.getRoleLevel())
            .build();
        
        AppUser savedUser = authService.registerUser(newUser, request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * Altera senha do usuário autenticado
     * 
     * POST /api/auth/change-password
     * Body: { "oldPassword": "...", "newPassword": "..." }
     */
    @PostMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestParam Long userId,
            @Valid @RequestBody ChangePasswordRequest request) {
        
        authService.changePassword(userId, request.getOldPassword(), request.getNewPassword());
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Senha alterada com sucesso");
        return ResponseEntity.ok(response);
    }

    /**
     * Logout (opcional - em JWT stateless, o cliente apenas descarta o token)
     */
    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Logout realizado com sucesso. Descarte os tokens.");
        return ResponseEntity.ok(response);
    }

    /**
     * Recupera informações do usuário autenticado
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(
            @RequestHeader("Authorization") String authorization) {
        
        // Implementação será completada com dados do SecurityContext
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("message", "Informações do usuário autenticado");
        userInfo.put("status", "authenticated");
        
        return ResponseEntity.ok(userInfo);
    }

    /**
     * Ativa ou reativa usuário (apenas ADMIN)
     */
    @PutMapping("/{userId}/activate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> activateUser(@PathVariable Long userId) {
        authService.reactivateUser(userId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário ativado com sucesso");
        return ResponseEntity.ok(response);
    }

    /**
     * Desativa usuário (apenas ADMIN)
     */
    @PutMapping("/{userId}/deactivate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Map<String, String>> deactivateUser(@PathVariable Long userId) {
        authService.deactivateUser(userId);
        
        Map<String, String> response = new HashMap<>();
        response.put("message", "Usuário desativado com sucesso");
        return ResponseEntity.ok(response);
    }

    // DTOs internos para requisições
    
    @lombok.Data
    static class RegisterUserRequest {
        private String email;
        private String password;
        private String fullName;
        private String registrationNumber;
        private Long organUnitId;
        private AppUser.RoleLevel roleLevel;
    }

    @lombok.Data
    static class ChangePasswordRequest {
        private String oldPassword;
        private String newPassword;
    }
}
