# Product API (Spring Boot)

Backend simple para una actividad de CRUD sobre `Producto`.

Cómo ejecutar localmente:

1. Configura variables de entorno (ejemplo para Windows PowerShell):

```powershell
$env:SPRING_DATASOURCE_URL='jdbc:postgresql://<host>:5432/<db>'
$env:SPRING_DATASOURCE_USERNAME='<user>'
$env:SPRING_DATASOURCE_PASSWORD='<password>'
``` 

2. Ejecuta:

```bash
mvn spring-boot:run
```

Luego accede a Swagger UI en `http://localhost:8080/swagger-ui/index.html`.

Nota: No subas credenciales al repositorio. Usa variables de entorno en Render.

Conectar a PostgreSQL en Render (u otro servicio remoto)

- PowerShell (Windows):

```powershell
$env:SPRING_DATASOURCE_URL='jdbc:postgresql://<render-host>:5432/<db>'
$env:SPRING_DATASOURCE_USERNAME='<user>'
$env:SPRING_DATASOURCE_PASSWORD='<password>'
mvn spring-boot:run
```

- CMD (Windows):

```cmd
set SPRING_DATASOURCE_URL=jdbc:postgresql://<render-host>:5432/<db>
set SPRING_DATASOURCE_USERNAME=<user>
set SPRING_DATASOURCE_PASSWORD=<password>
mvn spring-boot:run
```

- Bash (Linux/macOS):

```bash
export SPRING_DATASOURCE_URL='jdbc:postgresql://<render-host>:5432/<db>'
export SPRING_DATASOURCE_USERNAME='<user>'
export SPRING_DATASOURCE_PASSWORD='<password>'
mvn spring-boot:run
```

Ejecutar el JAR construido (alternativa):

```bash
mvn clean package
# Usando variables de entorno (Bash)
SPRING_DATASOURCE_URL='jdbc:postgresql://<render-host>:5432/<db>' \
SPRING_DATASOURCE_USERNAME='<user>' \
SPRING_DATASOURCE_PASSWORD='<password>' \
java -jar target/product-api-0.0.1-SNAPSHOT.jar
```

Si no quieres usar PostgreSQL local ni remoto, arranca con H2 en memoria (temporal):

```bash
SPRING_DATASOURCE_URL='jdbc:h2:mem:testdb' SPRING_DATASOURCE_USERNAME='sa' java -jar target/product-api-0.0.1-SNAPSHOT.jar
```

URLs útiles

- API base: `http://localhost:8080/api/productos`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html` ó `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

Ejemplos `curl` rápidos

- Listar:

```bash
curl http://localhost:8080/api/productos
```

- Crear:

```bash
curl -X POST -H "Content-Type: application/json" -d '{"nombre":"MiProd","descripcion":"Desc","precio":9.99}' http://localhost:8080/api/productos
```

Notas

- No incluyas credenciales en el repositorio.
- En Render, configura las variables de entorno equivalentes (`SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`) en la sección de Environment.
- Si quieres, puedo intentar arrancar la aplicación aquí usando tus credenciales; pégalos de forma segura o dime usar H2.
