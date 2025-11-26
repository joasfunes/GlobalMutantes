# 🧬 GlobalMutantes - API de Detección de Mutantes

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.4-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

API REST desarrollada para detectar si una secuencia de ADN pertenece a un mutante o a un humano. Proyecto desarrollado por **Joaquín Funes (50909)** para la materia Desarrollo de Software - 3K9.

---

## 📋 Descripción del Proyecto

Magneto, el poderoso mutante, necesita reclutar la mayor cantidad de mutantes para su causa. Para ello, te ha encomendado desarrollar un sistema que detecte si un humano es mutante basándose en su secuencia de ADN.

### ¿Cómo funciona?

Un humano es **mutante** si se encuentra **más de una secuencia** de cuatro letras iguales de forma:
- **Horizontal**
- **Vertical** 
- **Oblicua (diagonal)**

Las letras válidas del ADN son: `A`, `T`, `C`, `G` (representando cada base nitrogenada).

### Ejemplo de ADN Mutante

```
A T G C G A
C A G T G C
T T A T G T
A G A A G G
C C C C T A
T C A C T G
```

En este caso hay **2 secuencias** de 4 letras iguales:
- Horizontal: `CCCC` (fila 5)
- Vertical: `AAAA` (columna 4)

Por lo tanto, **es mutante**.

---

## 🚀 Tecnologías Utilizadas

### Backend
- **Java 17** - Lenguaje de programación
- **Spring Boot 3.3.4** - Framework principal
- **Spring Data JPA** - Capa de persistencia
- **Spring Validation** - Validación de datos
- **H2 Database** - Base de datos en memoria (desarrollo)

### Herramientas y Librerías
- **Lombok** - Reducción de código boilerplate
- **Swagger/OpenAPI 3** - Documentación interactiva de la API
- **JaCoCo** - Análisis de cobertura de código
- **Gradle** - Gestor de dependencias y construcción

### Deployment
- **Render** - Hosting de la aplicación

---

## 📁 Estructura del Proyecto

```
GlobalMutantes/
├── src/
│   ├── main/
│   │   ├── java/org/example/
│   │   │   ├── controller/       # Controladores REST
│   │   │   ├── service/          # Lógica de negocio
│   │   │   ├── repository/       # Acceso a datos
│   │   │   ├── entity/           # Entidades JPA
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── validator/        # Validadores personalizados
│   │   │   ├── exception/        # Manejo de excepciones
│   │   │   └── Main.java         # Clase principal
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     # Tests unitarios
├── build.gradle                  # Configuración de Gradle
└── README.md
```

---

## 🔧 Instalación y Configuración

### Prerrequisitos

- **Java 17** o superior
- **Gradle 8.x** (o usar el wrapper incluido)
- **Git**

### Clonar el Repositorio

```bash
git clone https://github.com/joasfunes/GlobalMutantes.git
cd GlobalMutantes
```

### Compilar el Proyecto

```bash
./gradlew build
```

### Ejecutar la Aplicación

```bash
./gradlew bootRun
```

La aplicación estará disponible en: `http://localhost:8080`

---

## 📡 Endpoints de la API

### 1. Verificar si un ADN es Mutante

**POST** `/mutant`

Verifica si una secuencia de ADN pertenece a un mutante.

**Request Body:**
```json
{
  "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
```

**Respuestas:**

- **200 OK** - Es mutante
```json
{
  "mutant": true
}
```

- **403 Forbidden** - Es humano
```json
{
  "mutant": false
}
```

- **400 Bad Request** - ADN inválido

---

### 2. Obtener Estadísticas

**GET** `/stats`

Devuelve las estadísticas de las verificaciones de ADN realizadas.

**Respuesta:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

**Descripción:**
- `count_mutant_dna`: Cantidad de ADNs mutantes detectados
- `count_human_dna`: Cantidad de ADNs humanos detectados
- `ratio`: Proporción de mutantes sobre humanos

---

## 🌐 Documentación Interactiva (Swagger)

Accede a la documentación completa de la API con Swagger UI:

**Local:**
```
http://localhost:8080/swagger-ui/index.html
```

**Producción:**
```
https://globalmutantes-0oey.onrender.com/swagger-ui/index.html
```

---

## 🧪 Ejemplos de Uso

### Con cURL

**Verificar ADN Mutante:**
```bash
curl -X POST https://globalmutantes-0oey.onrender.com/mutant \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  }'
```

**Obtener Estadísticas:**
```bash
curl https://globalmutantes-0oey.onrender.com/stats
```

### Con JavaScript (Fetch API)

```javascript
// Verificar ADN
fetch('https://globalmutantes-0oey.onrender.com/mutant', {
  method: 'POST',
  headers: {
    'Content-Type': 'application/json',
  },
  body: JSON.stringify({
    dna: ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
  })
})
.then(response => response.json())
.then(data => console.log(data));

// Obtener estadísticas
fetch('https://globalmutantes-0oey.onrender.com/stats')
.then(response => response.json())
.then(data => console.log(data));
```

### Con Python (Requests)

```python
import requests

# Verificar ADN
url = "https://globalmutantes-0oey.onrender.com/mutant"
dna_data = {
    "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
}
response = requests.post(url, json=dna_data)
print(response.json())

# Obtener estadísticas
stats_url = "https://globalmutantes-0oey.onrender.com/stats"
response = requests.get(stats_url)
print(response.json())
```

---

## 🧪 Testing

### Ejecutar Tests

```bash
./gradlew test
```

### Generar Reporte de Cobertura

```bash
./gradlew jacocoTestReport
```

El reporte HTML se genera en: `build/reports/jacoco/test/html/index.html`

### Verificar Cobertura Mínima (80%)

```bash
./gradlew jacocoTestCoverageVerification
```

---

## 🔍 Validaciones Implementadas

La API valida automáticamente:

✅ **Secuencia no nula ni vacía**  
✅ **Matriz cuadrada (NxN)**  
✅ **Solo letras válidas** (A, T, C, G)  
✅ **Tamaño mínimo** de matriz (4x4 o superior)

Ejemplos de ADN inválido:
```json
// Matriz no cuadrada
{"dna": ["ATGC","CAGT","TTAT"]}

// Letras inválidas
{"dna": ["ATGX","CAGT","TTAT","AGAA"]}

// Array vacío
{"dna": []}
```

---

## 🌐 Deployment en Render

La aplicación está desplegada en Render y disponible en:

**URL Base:** `https://globalmutantes-0oey.onrender.com`

### Configuración en Render

**Build Command:**
```bash
./gradlew build -x test
```

**Start Command:**
```bash
java -jar build/libs/GlobalMutantes-0.0.1-SNAPSHOT.jar
```

---

## 📊 Diagramas

### Diagrama de Secuencia - POST /mutant

El flujo completo de verificación incluye:
1. Validación de entrada
2. Búsqueda en base de datos
3. Análisis de secuencias (si es nuevo)
4. Persistencia del resultado
5. Respuesta HTTP apropiada

### Diagrama de Secuencia - GET /stats

El cálculo de estadísticas consulta:
1. Contador de ADNs mutantes
2. Contador de ADNs humanos
3. Cálculo del ratio

---

## 👨‍💻 Autor

**Joaquín Funes**  
Legajo: 50909  
Curso: 3K9  
Materia: Desarrollo de Software

---

## 📄 Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

---

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Para cambios importantes:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

---

## 📞 Contacto

Para consultas o sugerencias, puedes abrir un issue en el repositorio de GitHub.

**Repository:** [https://github.com/joasfunes/GlobalMutantes](https://github.com/joasfunes/GlobalMutantes)

---

⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub!
