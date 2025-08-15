<h1 align="center">Foro Hub Challenge 🖥️</h1> <p align="left"> <img src="https://img.shields.io/badge/STATUS-%20TERMINADO-green"> </p>

Aplicación Java con Spring Boot que implementa una API REST para gestión de usuarios con autenticación mediante JWT. Permite crear usuarios, autenticarse, obtener un token y acceder a endpoints protegidos. Incluye documentación automática con Swagger UI.

🔨 Funcionalidades principales

✅ Crear usuarios con nombre, correo y contraseña.

✅ Autenticación de usuarios mediante /login.

✅ Generación de JWT al autenticarse.

✅ Acceso a endpoints protegidos usando el token JWT en el header Authorization.

✅ Documentación automática de la API con Swagger UI.

# 🚀 Instrucciones de uso
Clonar el repositorio
```bash
git clone <url-del-repo>
cd foro-hub-challenge
```

# Configurar base de datos
Crea la base de datos que prefieras (PostgreSQL, MySQL, H2, etc.) y actualiza application.properties según tu elección:
```bash
spring.datasource.url=jdbc:postgresql://localhost/foro_api  # Cambiar según DB
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.flyway.locations=classpath:db/migration

api.security.token.secret=<tu_secreto_jwt>

```
# 📁 Estructura del Proyecto
```src/
src/
├── main/
│   ├── java/
│   │   ├── com.alura.foro.hub_challenge/
│   │   │   ├── controller/       ← Controladores REST (Usuarios, Login)
│   │   │   ├── domain/           ← Entidades JPA (Usuario, Perfil)
│   │   │   ├── infra/
│   │   │   │   ├── security/     ← Configuración de seguridad y JWT
│   │   │   ├── repository/       ← Repositorios Spring Data JPA
│   │   │   └── dto/              ← Clases DTO / Records (Login, Token)
│   └── resources/
│       └── application.properties ← Configuración general de la app
```
# 📦 Dependencias principales

Spring Boot Starter Web

Spring Boot Starter Data JPA

Spring Boot Starter Security

PostgreSQL Driver (o el driver de tu base de datos preferida)

Auth0 Java JWT (java-jwt)

SpringDoc OpenAPI (springdoc-openapi-starter-webmvc-ui)

Lombok

# ⚙️ Tecnologías Utilizadas
<p align="center"> <img src="https://skillicons.dev/icons?i=java,spring,postgresql,git" /> </p> - Java 17+ - Spring Boot 3 - Spring Security - JPA + Hibernate - Base de datos a elección (PostgreSQL, MySQL, H2, etc.) - JWT para autenticación - Swagger / OpenAPI para documentación

# 🔑 Ejemplos de uso

Crear usuario

POST /usuarios
```

{
"nombre": "Usuario1",
"email": "usuario1@email.com",
"contrasena": "12345"
}
```
Login

POST /login
```
{
  "email": "usuario1@email.com",
  "contrasena": "12345"
}
```
Respuesta:

```
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```
Acceso a endpoints protegidos

Enviar token en header:

```
Authorization: Bearer <token>
```
Swagger UI
```
http://localhost:8080/swagger-ui/index.html
```

# 🙌 Agradecimientos

Gracias a Alura Latam por la guía del desafío y a los recursos educativos que permitieron implementar autenticación y documentación de APIs con buenas prácticas en Spring Boot.