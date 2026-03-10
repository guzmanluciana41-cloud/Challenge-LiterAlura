# 📚 LiterAlura — Catálogo de Libros

<p align="center">
  <img src="https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java" />
  <img src="https://img.shields.io/badge/Spring%20Boot-3.2.3-green?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql" />
  <img src="https://img.shields.io/badge/Maven-4-red?style=for-the-badge&logo=apachemaven" />
  <img src="https://img.shields.io/badge/Status-Completo-brightgreen?style=for-the-badge" />
</p>

> **Challenge Back-End — Oracle Next Education (ONE) + Alura Latam**  
> Catálogo de libros interactivo por consola que consume la API Gutendex y persiste datos en PostgreSQL.

---

## 🗂️ Tabla de Contenidos

- [Sobre el proyecto](#-sobre-el-proyecto)
- [Funcionalidades](#-funcionalidades)
- [Tecnologías utilizadas](#-tecnologías-utilizadas)
- [Arquitectura del proyecto](#-arquitectura-del-proyecto)
- [Configuración del ambiente](#-configuración-del-ambiente)
- [Cómo ejecutar](#-cómo-ejecutar)
- [Demostración](#-demostración)
- [API Gutendex](#-api-gutendex)
- [Autor](#-autor)

---

## 📖 Sobre el proyecto

**LiterAlura** es una aplicación de consola desarrollada en Java con Spring Boot que permite:

- Buscar libros a través de la API pública **Gutendex** (más de 70.000 libros del Project Gutenberg)
- Registrar automáticamente libros y autores en una base de datos **PostgreSQL**
- Consultar, filtrar y visualizar estadísticas sobre el catálogo guardado

El proyecto fue desarrollado como parte del **Challenge Back-End** del programa **Oracle Next Education (ONE)** en colaboración con Alura Latam.

---

## ✅ Funcionalidades

| # | Funcionalidad | Descripción |
|---|---|---|
| 1 | 🔍 Buscar libro por título | Busca en la API Gutendex y registra en la BD |
| 2 | 📋 Listar libros registrados | Muestra todos los libros guardados, ordenados por descargas |
| 3 | 👤 Listar autores registrados | Lista todos los autores con sus libros asociados |
| 4 | 📅 Autores vivos en un año | Filtra autores que estaban vivos en un año específico |
| 5 | 🌐 Libros por idioma | Filtra libros por idioma (ES, EN, FR, PT) |
| 6 | 📊 Estadísticas por idioma | Cantidad de libros por idioma + estadísticas de descargas |
| 7 | 🏆 Top 10 más descargados | Los 10 libros con mayor número de descargas |

### Validaciones implementadas
- ❌ No permite registrar el mismo libro dos veces
- ❌ Muestra mensaje cuando el libro no existe en la API
- ❌ Valida entradas inválidas del usuario (años, idiomas, opciones de menú)

---

## 🛠️ Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 LTS | Lenguaje principal |
| Spring Boot | 3.2.3 | Framework base |
| Spring Data JPA | — | Persistencia ORM |
| PostgreSQL | 16+ | Base de datos relacional |
| Jackson Databind | 2.16.1 | Deserialización de JSON |
| Maven | 4+ | Gestión de dependencias |
| HttpClient (Java) | Built-in | Consumo de API REST |

---

## 🏗️ Arquitectura del proyecto

```
literalura/
├── src/
│   └── main/
│       ├── java/com/alura/literalura/
│       │   ├── LiteraluraApplication.java     # Clase principal (CommandLineRunner)
│       │   ├── dto/
│       │   │   ├── AutorDTO.java              # Record DTO del autor (API)
│       │   │   ├── LibroDTO.java              # Record DTO del libro (API)
│       │   │   └── RespuestaApiDTO.java       # Record DTO de la respuesta
│       │   ├── model/
│       │   │   ├── Autor.java                 # Entidad JPA Autor
│       │   │   └── Libro.java                 # Entidad JPA Libro
│       │   ├── repository/
│       │   │   ├── AutorRepository.java       # JpaRepository + derived queries
│       │   │   └── LibroRepository.java       # JpaRepository + derived queries
│       │   ├── service/
│       │   │   ├── ConsumoAPI.java            # HttpClient para la API
│       │   │   ├── ConvierteDatos.java        # ObjectMapper (Jackson)
│       │   │   └── LibroService.java          # Lógica de negocio
│       │   └── principal/
│       │       └── Principal.java             # Menú e interacción con usuario
│       └── resources/
│           └── application.properties         # Configuración BD
└── pom.xml
```

### Diagrama de Relaciones (BD)

```
┌──────────────────────┐       ┌──────────────────────────┐
│        AUTORES       │       │         LIBROS           │
├──────────────────────┤       ├──────────────────────────┤
│ id (PK)              │◄──┐   │ id (PK)                  │
│ nombre (UNIQUE)      │   └───│ autor_id (FK)            │
│ anio_nacimiento      │       │ titulo (UNIQUE)          │
│ anio_fallecimiento   │       │ idioma                   │
└──────────────────────┘       │ num_descargas            │
                               └──────────────────────────┘
```

---

## ⚙️ Configuración del ambiente

### Pre-requisitos

Asegúrate de tener instalado:

- [Java JDK 17+](https://www.oracle.com/java/technologies/downloads/)
- [Maven 4+](https://maven.apache.org/download.cgi)
- [PostgreSQL 16+](https://www.postgresql.org/download/)
- IDE recomendado: [IntelliJ IDEA](https://www.jetbrains.com/idea/download/)

### Configurar la base de datos

1. Abre PostgreSQL y ejecuta:

```sql
CREATE DATABASE literalura;
```

2. Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost/literalura
spring.datasource.username=postgres
spring.datasource.password=TU_CONTRASEÑA
```

> **Tip:** También puedes usar variables de entorno:
> ```
> DB_HOST=localhost
> DB_NAME=literalura
> DB_USER=postgres
> DB_PASSWORD=tu_password
> ```

---

## 🚀 Cómo ejecutar

### Desde la terminal

```bash
# 1. Clona el repositorio
git clone https://github.com/tu-usuario/literalura.git
cd literalura

# 2. Compila el proyecto
mvn clean install

# 3. Ejecuta la aplicación
mvn spring-boot:run
```

### Desde IntelliJ IDEA

1. Importa el proyecto como proyecto Maven
2. Configura las variables de entorno de la base de datos (Run → Edit Configurations)
3. Ejecuta `LiteraluraApplication.java`

---

## 🎮 Demostración

```
  ██╗     ██╗████████╗███████╗██████╗  █████╗ ██╗     ██╗   ██╗██████╗  █████╗
  ██║     ██║╚══██╔══╝██╔════╝██╔══██╗██╔══██╗██║     ██║   ██║██╔══██╗██╔══██╗
  ██║     ██║   ██║   █████╗  ██████╔╝███████║██║     ██║   ██║██████╔╝███████║
  ███████╗██║   ██║   ███████╗██║  ██║██║  ██║███████╗╚██████╔╝██║  ██║██║  ██║
              📚 Catálogo de Libros - Oracle ONE Challenge 📚

╔════════════════════════════════════════════╗
║           MENÚ PRINCIPAL                  ║
╠════════════════════════════════════════════╣
║  1 - Buscar libro por título               ║
║  2 - Listar libros registrados             ║
║  3 - Listar autores registrados            ║
║  4 - Listar autores vivos en un año        ║
║  5 - Listar libros por idioma              ║
║  6 - Estadísticas de libros por idioma     ║
║  7 - Top 10 libros más descargados         ║
║  0 - Salir                                 ║
╚════════════════════════════════════════════╝
  Selecciona una opción: 1

  ── BUSCAR LIBRO POR TÍTULO ──
  Ingresa el título: Pride and Prejudice

  ✔ Libro encontrado y registrado:
┌─────────────────────────────────────────┐
│ Título   : Pride and Prejudice          │
│ Autor    : Austen, Jane                 │
│ Idioma   : en                           │
│ Descargas: 6493                         │
└─────────────────────────────────────────┘
```

---

## 🌐 API Gutendex

Esta aplicación consume la API [**Gutendex**](https://gutendex.com/), que indexa el catálogo del [Project Gutenberg](https://www.gutenberg.org/).

| Endpoint | Descripción |
|---|---|
| `GET /books/` | Lista todos los libros |
| `GET /books/?search=titulo` | Busca libros por título |

**Ejemplo de respuesta:**
```json
{
  "results": [
    {
      "title": "Pride and Prejudice",
      "authors": [{ "name": "Austen, Jane", "birth_year": 1775, "death_year": 1817 }],
      "languages": ["en"],
      "download_count": 6493
    }
  ]
}
```

---

## 👨‍💻 Autor

Desarrollado como parte del **Challenge LiterAlura** — Oracle Next Education (ONE) + Alura Latam.

- 🎓 Programa: Oracle Next Education (ONE)
- 🏫 Partner: Alura Latam
- 📌 Challenge: Back-End — Catálogo de Libros


