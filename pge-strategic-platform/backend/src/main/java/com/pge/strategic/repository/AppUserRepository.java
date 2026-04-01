package com.pge.strategic.repository;

import com.pge.strategic.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório para gestão de usuários do sistema
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Busca usuário por e-mail
     */
    Optional<AppUser> findByEmail(String email);

    /**
     * Verifica se e-mail já existe
     */
    boolean existsByEmail(String email);

    /**
     * Busca usuários por unidade organizacional
     */
    List<AppUser> findByOrganUnitId(Long organUnitId);

    /**
     * Busca usuários ativos por nível de role
     */
    List<AppUser> findByRoleLevelAndIsActiveTrue(AppUser.RoleLevel roleLevel);

    /**
     * Busca todos os usuários ativos
     */
    List<AppUser> findByIsActiveTrue();

    /**
     * Busca usuários por matrícula
     */
    Optional<AppUser> findByRegistrationNumber(String registrationNumber);

    /**
     * Conta usuários por unidade organizacional
     */
    long countByOrganUnitId(Long organUnitId);

    /**
     * Conta usuários ativos por nível de role
     */
    long countByRoleLevelAndIsActiveTrue(AppUser.RoleLevel roleLevel);

    /**
     * Busca usuários pelo nome completo (LIKE)
     */
    @Query("SELECT u FROM AppUser u WHERE LOWER(u.fullName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<AppUser> findByNameContaining(@Param("name") String name);

    /**
     * Atualiza data do último login
     */
    @Query("UPDATE AppUser u SET u.lastLogin = :lastLogin WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("lastLogin") java.time.LocalDateTime lastLogin);
}
