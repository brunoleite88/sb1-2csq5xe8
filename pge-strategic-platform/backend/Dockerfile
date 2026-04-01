# Dockerfile para Backend Spring Boot - PGE Strategic Platform
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copiar arquivos Maven
COPY pom.xml .
COPY src ./src

# Build da aplicação
RUN apk add --no-cache maven && \
    mvn clean package -DskipTests -Dmaven.test.skip=true

# Runtime image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Instalar curl para healthcheck
RUN apk add --no-cache curl

# Copiar o JAR construído
COPY --from=builder /app/target/*.jar app.jar

# Criar diretórios para logs e uploads
RUN mkdir -p /app/logs /app/uploads

# Exposição da porta
EXPOSE 8080

# Variáveis de ambiente
ENV SPRING_PROFILES_ACTIVE=docker
ENV JAVA_OPTS="-Xms512m -Xmx1g -Djava.security.egd=file:/dev/./urandom"

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD curl -f http://localhost:8080/forpdi/actuator/health || exit 1

# Executar a aplicação
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
