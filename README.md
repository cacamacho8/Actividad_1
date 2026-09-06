# Product API (Spring Boot + REST + GraphQL)

Backend para una actividad de CRUD sobre `Producto`, implementado con Spring Boot,
Spring Data JPA, PostgreSQL y dos formas de exponer los mismos datos: REST y GraphQL.

## Integracion de GraphQL

Se selecciono Spring Boot como framework y `spring-boot-starter-graphql` como libreria.
Maven instala la libreria y sus dependencias a partir de esta entrada en `pom.xml`.

El esquema se encuentra en `src/main/resources/graphql/schema.graphqls` y los resolvers
en `ProductoGraphQLController`. Estos resolvers reutilizan `ProductoService`, por lo que
REST y GraphQL aplican la misma logica de negocio y persisten en la misma tabla.

Al iniciar la aplicacion, los endpoints son:

- GraphQL: `http://localhost:8080/graphql`
- GraphiQL (interfaz para probar consultas): `http://localhost:8080/graphiql`

### Consulta de productos

```graphql
query {
	productos {
		id
		nombre
		descripcion
		precio
	}
}
```

### Buscar un producto

```graphql
query {
	producto(id: 1) {
		id
		nombre
		precio
	}
}
```

### Crear, actualizar y eliminar

```graphql
mutation {
	crearProducto(input: {
		nombre: "Teclado"
		descripcion: "Mecanico"
		precio: 49.99
	}) {
		id
		nombre
		precio
	}
}
```

```graphql
mutation {
	actualizarProducto(id: 1, input: {
		nombre: "Teclado Pro"
		descripcion: "Mecanico RGB"
		precio: 69.99
	}) {
		id
		nombre
		descripcion
		precio
	}
}
```

```graphql
mutation {
	eliminarProducto(id: 1)
}
```

GraphQL usa un `Float` para transportar `precio`; la aplicacion lo convierte a
`BigDecimal` antes de guardar el producto.

## GraphQL frente a REST

REST organiza la API alrededor de recursos y verbos HTTP: `GET /api/productos`,
`POST /api/productos`, `PUT /api/productos/{id}` y `DELETE /api/productos/{id}`.
GraphQL usa normalmente un unico endpoint (`/graphql`) y el cliente declara en la
consulta los campos que necesita. Esto evita respuestas con campos sobrantes y permite
combinar datos relacionados en una sola solicitud.

REST aprovecha directamente los codigos HTTP y suele tener varios endpoints; GraphQL
devuelve un formato de respuesta uniforme con `data` y, cuando corresponde, `errors`.
GraphQL no reemplaza JPA ni el servicio: en este proyecto solo cambia la capa de acceso.

## Guion sugerido para el video (3-5 minutos)

1. (30-45 s) Presentar el proyecto, Spring Boot, Java 17, Maven, PostgreSQL y el CRUD REST existente.
2. (45-60 s) Mostrar `pom.xml` y explicar `spring-boot-starter-graphql` como dependencia de instalacion.
3. (45-60 s) Mostrar `schema.graphqls`: tipos, `Query`, `Mutation` y `ProductoInput`.
4. (60-90 s) Ejecutar la aplicacion, abrir GraphiQL y demostrar listar, buscar, crear, actualizar y eliminar.
5. (30-45 s) Explicar que ambos accesos reutilizan `ProductoService` y resumir diferencias entre REST y GraphQL.

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


cd C:\project
set SPRING_DATASOURCE_URL=jdbc:postgresql://dpg-d9vp6fojo6nc73b195g0-a.virginia-postgres.render.com:5432/actividad1_mnty
set SPRING_DATASOURCE_USERNAME=carloscamacho
set SPRING_DATASOURCE_PASSWORD=WZBoYf21oGv8oXlC6AuTDZUE0SjVlSQz
mvn -DskipTests spring-boot:run