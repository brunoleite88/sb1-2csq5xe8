package com.pge.strategic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Aplicação principal da PGE Strategic Platform
 * 
 * Sistema de Gestão Estratégica para Procuradoria Geral do Estado
 * com customização completa pelo administrador.
 */
@SpringBootApplication
@EnableScheduling
@EnableSpringDataWebSupport
@EnableConfigurationProperties
public class PgeStrategicPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(PgeStrategicPlatformApplication.class, args);
        System.out.println("============================================");
        System.out.println("  PGE Strategic Platform iniciada com sucesso!");
        System.out.println("  Acesse: http://localhost:8080/pge");
        System.out.println("  Swagger: http://localhost:8080/pge/swagger-ui.html");
        System.out.println("============================================");
    }
}
