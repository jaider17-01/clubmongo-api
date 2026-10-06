# ClubMongo — Relaciones con Spring Data MongoDB

Versión en MongoDB del ejercicio de relaciones JPA (Club, Entrenador, Jugador, Asociación, Competición).

## Ejecutar localmente con Docker

Requiere Docker Desktop. Desde la raíz del proyecto:

```bash
docker compose up --build
```

La API queda en http://localhost:8103 y MongoDB en el puerto 27017. Los datos
se guardan en un volumen persistente. El cargador de datos de ejemplo solo
inserta registros cuando la colección de clubes está vacía; los reinicios no
eliminan datos.

## Ejecutar en Spring Tool Suite (STS)

1. Descomprime `clubmongo-api.zip`.
2. `File > Import > Maven > Existing Maven Projects` y selecciona la carpeta `clubmongo-api`.
3. Espera a que Maven descargue las dependencias (requiere Java 17).
4. Clic derecho en el proyecto > `Run As > Spring Boot App`.
5. Configura `MONGODB_URI` si la instancia de MongoDB no está en `localhost:27017`.
6. La API queda en http://localhost:8103 y carga datos de ejemplo solo si no hay clubes.

### Conexión a MongoDB

- La aplicación lee la conexión desde la variable de entorno `MONGODB_URI`.
- En Render, define `MONGODB_URI` con una URI de MongoDB Atlas en las variables
  privadas del servicio. No guardes credenciales en el repositorio ni en el Dockerfile.
- En Atlas, en **Network Access**, la IP desde donde corres el proyecto debe estar permitida
  (o `0.0.0.0/0` solo para pruebas); si no, la conexión da *timeout*.
- Render asigna el puerto HTTP mediante `PORT`; localmente el valor predeterminado es `8103`.

## JPA → MongoDB

| Relación (JPA) | Entidad | En Mongo |
|---|---|---|
| `@OneToOne` | Club → Entrenador | Documento **embebido** dentro de `club` |
| `@OneToMany` + `@JoinColumn` | Club → Jugador | `@DocumentReference(lazy = true)`: lista de ids en `club` |
| `@ManyToOne` | Club → Asociación | `@DocumentReference`: un id en `club` |
| `@ManyToMany` | Club → Competición | `@DocumentReference(lazy = true)`: lista de ids, sin tabla intermedia |

Lo que desaparece: tablas intermedias, `@JoinColumn`, `FetchType` en las anotaciones y las FK reales.
El `@Id` pasa de `Long` a `String` (ObjectId).

## Integridad referencial (lo que antes hacía la BD)

Mongo no valida referencias. `ClubService` reemplaza las FK:

- Crear/editar un club con un id inexistente → **404**.
- Un jugador solo puede estar en un club (semántica real de uno-a-muchos) → **409** si ya pertenece a otro.
- Borrar una asociación con clubes afiliados → **409** (equivalente a FK `NO ACTION`).
- Borrar un jugador o competición → se quita de los clubes que lo referencian (equivalente a `ON DELETE CASCADE`).

## Endpoints

| Recurso | Ruta |
|---|---|
| Clubes | `/api/clubes` |
| Jugadores | `/api/jugadores` |
| Asociaciones | `/api/asociaciones` |
| Competiciones | `/api/competiciones` |

Todos con `GET` (lista), `GET /{id}`, `POST`, `PUT /{id}` y `DELETE /{id}`.

### Ejemplo de uso

```bash
# 1. Crear los documentos referenciados
curl -X POST localhost:8103/api/asociaciones -H 'Content-Type: application/json' \
  -d '{"nombre":"Federación Colombiana de Fútbol","pais":"Colombia","presidente":"Ejemplo","siglas":"FCF"}'

curl -X POST localhost:8103/api/jugadores -H 'Content-Type: application/json' \
  -d '{"nombre":"Mateo","apellido":"Vargas","numero":9,"posicion":"Delantero"}'

curl -X POST localhost:8103/api/competiciones -H 'Content-Type: application/json' \
  -d '{"nombre":"Copa Postobón","montoPremio":1000000,"fechaInicio":"2026-03-01","fechaFin":"2026-10-30"}'

# 2. Crear el club usando los ids devueltos
curl -X POST localhost:8103/api/clubes -H 'Content-Type: application/json' -d '{
  "nombre": "Santa Fe Demo",
  "entrenador": {"nombre":"Juan","apellido":"Pérez","edad":48,"nacionalidad":"Colombiana"},
  "asociacionId": "<ID_ASOCIACION>",
  "jugadoresIds": ["<ID_JUGADOR>"],
  "competicionesIds": ["<ID_COMPETICION>"]
}'
```

## Estructura

```
src/main/java/com/uts/clubmongo/
├── ClubMongoApplication.java
├── model/        Club, Entrenador (embebido), Jugador, Asociacion, Competicion, ClubRequest
├── repository/   un MongoRepository por documento
├── service/      ClubService (reglas de integridad)
├── controller/   un controlador REST por recurso
└── config/       DatosIniciales (seed)
```
