<a id="top"></a>

<div align="center">
  <img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 17">
  <img src="https://img.shields.io/badge/Spring_Boot-4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Spring_Security-JWT-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white" alt="Spring Security">
  <img src="https://img.shields.io/badge/MySQL-8.0-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL">
  <img src="https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white" alt="JUnit 5">
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven">
  <img src="https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" alt="Swagger">
  <img src="https://img.shields.io/badge/Estado-Activo-success?style=for-the-badge" alt="Estado">
  <img src="https://img.shields.io/github/license/Jesus-0046/securetrack?style=for-the-badge&color=blue" alt="Licencia">
</div>

# 🔐 SecureTrack - Incidence Management API

API REST para gestión de incidencias informáticas con autenticación JWT y control de acceso basado en roles.

Desarrollado como proyecto de fin de ciclo DAM con enfoque en seguridad y buenas prácticas.

## 🚀 Demo

API en producción: [https://securetrack-qqll.onrender.com](https://securetrack-qqll.onrender.com)

Documentación Swagger: [https://securetrack-qqll.onrender.com/swagger-ui/index.html](https://securetrack-qqll.onrender.com/swagger-ui/index.html)

### Credenciales de prueba

| Usuario | Contraseña | Rol |
|---------|------------|-----|
| `admin` | `admin123` | ADMIN |
| `user1` | `user123` | USER |

## 🛠 Stack Tecnológico

| Capa | Tecnología |
|------|------------|
| Lenguaje | Java 17 |
| Framework | Spring Boot |
| Seguridad | Spring Security + JWT |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | MySQL |
| Documentación | Swagger / OpenAPI 3 |
| Tests | JUnit 5 + Mockito |
| Build | Maven |

## 📦 Instalación

```bash
# Clonar repositorio
git clone https://github.com/Jesus-0046/securetrack.git
cd securetrack

# Crear base de datos MySQL
# Ejecuta en tu cliente MySQL:
CREATE DATABASE securetrack;

# Configurar variable de entorno con tu contraseña de MySQL
# En Windows (CMD): set MYSQL_PASSWORD=tu_contraseña
# En Linux/Mac: export MYSQL_PASSWORD=tu_contraseña

# Ejecutar
mvn clean install
mvn spring-boot:run
```


## 📚 Endpoints principales

| Método | Endpoint | Rol |
|--------|----------|-----|
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/login` | Público |
| GET | `/api/incidences` | USER, ADMIN |
| GET | `/api/incidences/{id}` | USER, ADMIN |
| POST | `/api/incidences` | USER, ADMIN |
| PUT | `/api/incidences/{id}` | USER, ADMIN |
| PATCH | `/api/incidences/{id}/assign/{userId}` | ADMIN |
| DELETE | `/api/incidences/{id}` | ADMIN |
| GET | `/api/incidences/status/{status}` | USER, ADMIN |
| GET | `/api/incidences/priority/{priority}` | USER, ADMIN |
| GET | `/api/incidences/search?keyword=` | USER, ADMIN |


## 🔒 Seguridad

- ✅ JWT con tokens firmados con HMAC-SHA256
- ✅ Passwords hasheados con BCrypt
- ✅ Control de acceso basado en roles (ADMIN / USER)
- ✅ Validaciones de entrada con Bean Validation
- ✅ Manejo centralizado de excepciones


## 🧪 Tests

```bash
mvn test
6 tests unitarios con JUnit 5 y Mockito.
```

## 📬 Postman Collection
Importa el archivo SecureTrack.postman_collection.json en Postman para probar todos los endpoints.


## 👤 Autor



**Jesús** - Estudiante de DAM

Portfolio: https://jesus-portfolio-iota.vercel.app

GitHub: [@Jesus-0046](https://github.com/Jesus-0046)
