# Plan de trabajo — SQLite, modelos y persistencia base

## Responsable
- Desarrollador: Desarrollador 2 (Persona 2)
- Responsabilidad: Diseño de base de datos local y persistencia de datos
- Estado general: Completado
- Porcentaje de avance: 100%

## Objetivo
Diseñar e implementar la infraestructura de persistencia local utilizando SQLite, definiendo los modelos de datos necesarios y asegurando la integridad de la información financiera asociada a cada usuario de Firebase.

## Alcance
- Definición de modelos de datos Kotlin (Usuario, Categoria, Movimiento, Presupuesto, Alerta, etc.).
- Implementación de `HelperDB.kt` con el esquema completo de tablas.
- Creación de métodos CRUD genéricos y específicos para la gestión de datos.
- Pre-carga de categorías financieras estándar.
- Gestión de la relación entre datos locales y el `firebase_uid`.

## Dependencias
- **Desarrollador 1 (Auth):** El `firebase_uid` es obligatorio para asociar todos los registros.
- **Desarrollador 3 (Movimientos):** Requiere los métodos CRUD de movimientos e historial.
- **Desarrollador 4 (Presupuestos):** Requiere los métodos CRUD de presupuestos y alertas.

## Archivos involucrados
- `db/HelperDB.kt`
- `model/Usuario.kt` (Actualizado)
- `model/Categoria.kt`
- `model/Movimiento.kt`
- `model/Presupuesto.kt`
- `model/Alerta.kt`
- `model/Etiqueta.kt`
- `model/MovimientoEtiqueta.kt`

## Tareas ejecutables

| ID | Tarea | Estado | Porcentaje | Observaciones |
|---|---|---|---:|---|
| T01 | Definición de modelos de datos (Data Classes) | Completado | 100% | Creados: Usuario, Categoria, Movimiento, Presupuesto, Alerta |
| T02 | Configuración del esquema completo en `HelperDB.kt` | Completado | 100% | Todas las tablas (usuarios, categorias, movimientos, presupuestos, alertas) definidas. |
| T03 | Implementación de métodos CRUD para Categorías | Completado | 100% | CategoriaController implementado. |
| T04 | Implementación de métodos CRUD para Movimientos | Completado | 100% | MovimientoController implementado. |
| T05 | Implementación de métodos CRUD para Presupuestos y Alertas | Completado | 100% | PresupuestoController implementado. |
| T06 | Lógica de pre-carga de categorías por defecto | Completado | 100% | 12 categorías base insertadas en onCreate. |
| T07 | Validación de integridad de datos y llaves foráneas | Completado | 100% | Activado PRAGMA foreign_keys = ON. |

## Criterios de aceptación
- Todas las tablas definidas en `SKILL.md` existen en el archivo `.db`.
- Los datos financieros se filtran correctamente por el `firebase_uid`.
- La base de datos se crea correctamente al iniciar la aplicación.
- Las categorías por defecto están disponibles desde el primer uso.
- Los modelos de datos son consistentes con las tablas de SQLite.

## Registro de avances

| Fecha | Cambio realizado | Archivos afectados | Avance actualizado |
|---|---|---|---|
| 2024-05-22 | Creación del plan de trabajo para Persona 2 | `docs/Fase_2_sqlite_modelos_persistencia_desarrollador_2_plan.md` | 5% |
| 2024-05-22 | Implementación de Perfil, PerfilController y HelperDB base | `PerfilActivity.kt`, `PerfilController.kt`, `HelperDB.kt`, `activity_perfil.xml` | 20% |
| 2024-05-22 | Creación de modelos Categoria, Movimiento, Presupuesto, Alerta | `model/` | 35% |
| 2024-05-22 | Actualización de esquema completo en HelperDB.kt | `db/HelperDB.kt` | 45% |
| 2024-05-22 | Implementación de CRUD de Categorías y precarga | `CategoriaController.kt`, `HelperDB.kt` | 65% |
| 2024-05-22 | Implementación de CRUD de Movimientos, Presupuestos y Alertas | `MovimientoController.kt`, `PresupuestoController.kt` | 100% |
| 2024-05-22 | Corrección de versión de DB y verificación de test exitoso | `HelperDB.kt`, `MainActivity.kt` | 100% |

## Resultado esperado
Una base de datos SQLite robusta y bien estructurada, con una capa de modelos clara que permita al resto de los desarrolladores interactuar con la persistencia de forma sencilla y segura.
