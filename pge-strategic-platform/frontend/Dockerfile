# Dockerfile para Frontend React + Apache - PGE Strategic Platform
FROM node:18-alpine AS builder

WORKDIR /app

# Copiar package.json e instalar dependências
COPY package*.json ./
RUN npm ci --only=production

# Copiar código fonte e build
COPY . .

# Argumentos de build para configuração
ARG REACT_APP_API_URL=http://localhost:8080/forpdi
ARG REACT_APP_RECAPTCHA_KEY=

ENV REACT_APP_API_URL=$REACT_APP_API_URL
ENV REACT_APP_RECAPTCHA_KEY=$REACT_APP_RECAPTCHA_KEY

# Build da aplicação React
RUN npm run build

# Runtime image com Apache
FROM httpd:2.4-alpine

# Copiar build do React para o DocumentRoot do Apache
COPY --from=builder /app/build /usr/local/apache2/htdocs/

# Copiar configuração customizada do Apache
COPY httpd.conf /usr/local/apache2/conf/httpd.conf
COPY httpd-vhosts.conf /usr/local/apache2/conf/extra/httpd-vhosts.conf

# Instalar módulos necessários do Apache
RUN sed -i 's/#LoadModule rewrite_module/LoadModule rewrite_module/' /usr/local/apache2/conf/httpd.conf && \
    sed -i 's/#LoadModule headers_module/LoadModule headers_module/' /usr/local/apache2/conf/httpd.conf && \
    sed -i 's/#LoadModule ssl_module/LoadModule ssl_module/' /usr/local/apache2/conf/httpd.conf

# Expor portas HTTP e HTTPS
EXPOSE 80 443

# Healthcheck
HEALTHCHECK --interval=30s --timeout=10s --retries=3 \
    CMD wget -q --spider http://localhost:80/ || exit 1

# Iniciar Apache
CMD ["httpd-foreground"]
