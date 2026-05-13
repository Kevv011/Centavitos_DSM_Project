# Plan de trabajo — Movimientos financieros e historial

## Responsable
- Desarrollador: Desarrollador 3 (Persona 3)
- Responsabilidad: Gestión de ingresos/gastos e historial financiero
- Estado general: Completado
- Porcentaje de avance: 100%

## Objetivo
Implementar el núcleo operativo de la aplicación que permita a los usuarios registrar, visualizar y gestionar sus movimientos financieros (ingresos y gastos), proporcionando herramientas de búsqueda y filtrado para el análisis de sus datos.

## Alcance
- Formulario para registro de ingresos y gastos.
- Visualización de movimientos en listas dinámicas (RecyclerView).
- Funcionalidades de edición y eliminación de registros.
- Pantalla de historial con filtros avanzados (fecha, categoría, tipo, monto).
- Búsqueda de movimientos por descripción.
- Validaciones de entrada de datos financieros.

## Dependencias
- **Desarrollador 1 (Auth):** Necesario el `firebase_uid` para asociar los movimientos al usuario.
- **Desarrollador 2 (SQLite):** Uso de `HelperDB.kt` y `MovimientoController.kt` (ampliación de métodos si es necesario).
- **Desarrollador 5 (UI/Integración):** Aplicación de los estilos de `DESIGN.md`.

## Archivos involucrados
- `ui/MovimientoFormActivity.kt`, `layout/activity_movimiento_form.xml`
- `ui/HistorialActivity.kt`, `layout/activity_historial.xml`
- `adapter/MovimientoAdapter.kt`, `layout/item_movimiento.xml`
- `controller/MovimientoController.kt` (Ampliación)
- `model/Movimiento.kt`

## Tareas ejecutables

| ID | Tarea | Estado | Porcentaje | Observaciones |
|---|---|---|---:|---|
| T01 | Diseño y creación del Layout para formulario de movimientos | Completado | 100% | Layout activity_movimiento_form.xml creado |
| T02 | Implementación de `MovimientoFormActivity` (Lógica de guardado) | Completado | 100% | Lógica de inserción y validación lista |
| T03 | Diseño del item para RecyclerView (`item_movimiento.xml`) | Completado | 100% | Diseño Mintlify con icono circular y colores por tipo |
| T04 | Creación de `MovimientoAdapter` para listas dinámicas | Completado | 100% | Soporta MovimientoExtended para mostrar nombres de categorías |
| T05 | Implementación de `HistorialActivity` (Lista general) | Completado | 100% | Carga y visualización de movimientos desde SQLite |
| T06 | Implementación de filtros en el Historial | Completado | 100% | Filtros por Tipo (ChipGroup) integrados |
| T07 | Funcionalidad de Búsqueda por texto | Completado | 100% | Búsqueda dinámica por descripción y categoría |
| T08 | Integración de opciones de Edición y Eliminación | Completado | 100% | CRUD completo: Editar (clic) y Eliminar (long clic) |

## Criterios de aceptación
- El usuario puede guardar ingresos y gastos correctamente.
- La lista de movimientos muestra el icono y color de la categoría.
- Los filtros de historial devuelven los registros exactos solicitados.
- Se valida que el monto sea mayor a cero antes de guardar.
- El diseño es coherente con las otras pantallas (Mintlify Style).
- La eliminación de un registro se refleja inmediatamente en la lista.

## Registro de avances

| Fecha | Cambio realizado | Archivos afectados | Avance actualizado |
|---|---|---|---|
| 2024-05-22 | Creación del plan de trabajo para Persona 3 | `docs/Fase_3_movimientos_historial_desarrollador_3_plan.md` | 0% |
| 2024-05-22 | Implementación de Formulario de Movimientos (T01, T02) | `activity_movimiento_form.xml`, `MovimientoFormActivity.kt` | 25% |
| 2024-05-22 | Creación de Adapter e Historial (T03, T04, T05) | `item_movimiento.xml`, `MovimientoAdapter.kt`, `HistorialActivity.kt` | 60% |
| 2024-05-22 | Implementación de Filtros, Búsqueda y Edición/Eliminación (T06, T07, T08) | `HistorialActivity.kt`, `MovimientoFormActivity.kt`, `MovimientoController.kt` | 100% |

## Resultado esperado
Un módulo funcional de gestión financiera que permita al estudiante llevar un control detallado de su dinero, con una interfaz fluida y herramientas potentes de consulta.
