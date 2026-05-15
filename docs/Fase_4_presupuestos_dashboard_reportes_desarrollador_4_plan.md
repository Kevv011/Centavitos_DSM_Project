# Plan de trabajo — Desarrollador 4: Presupuestos, Alertas, Dashboard y Reportes

## Responsable
- **Desarrollador:** Asistente AI (en representación del Desarrollador 4)
- **Responsabilidad:** Gestión de presupuestos, sistema de alertas financieras, visualización de dashboard principal y generación de reportes gráficos.
- **Estado general:** Finalizado
- **Porcentaje de avance:** 100%

## Objetivo
Implementar los módulos de control financiero y visualización de datos de la aplicación, permitiendo al usuario monitorear su estado económico actual, establecer límites de gasto por categoría y analizar su comportamiento financiero mediante reportes.

## Alcance
- **Dashboard:** Resumen mensual (ingresos, gastos, saldo), alertas críticas y accesos rápidos.
- **Presupuestos:** CRUD de presupuestos mensuales por categoría y visualización de progreso.
- **Alertas:** Lógica de comparación de gastos vs presupuestos y notificaciones visuales en la UI.
- **Reportes:** Visualización de distribución de gastos y comparativas financieras.

## Dependencias
- **Desarrollador 1 (Autenticación):** Necesito el `firebase_uid` para filtrar todos los datos en SQLite.
- **Desarrollador 2 (SQLite):** Dependo de la creación de las tablas `movimientos`, `presupuestos`, `categorias` y `alertas` en `HelperDB.kt`.
- **Desarrollador 3 (Movimientos):** Los cálculos de gastos acumulados dependen de los datos registrados en el módulo de movimientos.
- **Desarrollador 5 (UI/UX):** Lineamientos finales de diseño y recursos gráficos según `DESIGN.md`.

## Archivos involucrados
- **Activities:** `DashboardActivity.kt`, `PresupuestoActivity.kt`, `ReportesActivity.kt`.
- **Controllers:** `DashboardController.kt`, `PresupuestoController.kt`.
- **Adapters:** `PresupuestoAdapter.kt`.
- **Layouts:** `activity_dashboard.xml`, `activity_presupuestos.xml`, `activity_reportes.xml`, `item_presupuesto.xml`.

## Tareas ejecutables

| ID | Tarea | Estado | Porcentaje | Observaciones |
|---|---|---|---:|---|
| T01 | Creación y estructuración del plan de trabajo | Finalizado | 100% | Documento inicial creado. |
| T02 | Diseño y maquetación del Dashboard (`activity_dashboard.xml`) | Finalizado | 100% | Layout y actividad base creados. |
| T03 | Implementación de `DashboardController` y lógica de resumen | Finalizado | 100% | Cálculo de saldo, ingresos y gastos centralizado. |
| T04 | Diseño de interfaz de Presupuestos (`activity_presupuestos.xml`, `item_presupuesto.xml`) | Finalizado | 100% | Lista de categorías con barras de progreso. |
| T05 | Implementación de CRUD de Presupuestos en `PresupuestoController` | Finalizado | 100% | Operaciones en SQLite para límites mensuales. |
| T06 | Lógica de Alertas de Presupuesto | Finalizado | 100% | Generación automática de mensajes de advertencia. |
| T07 | Desarrollo de `PresupuestoAdapter` para RecyclerView | Finalizado | 100% | Visualización dinámica de los límites de gasto. |
| T08 | Diseño y desarrollo de Reportes (`activity_reportes.xml`, `ReportesActivity`) | Finalizado | 100% | Visualización de distribución de gastos por categoría. |
| T09 | Integración de filtrado por `firebase_uid` en consultas | Finalizado | 100% | Privacidad de datos asegurada por usuario. |
| T10 | Pruebas de integración con módulos de Movimientos y SQLite | Finalizado | 100% | Verificar que los gastos se reflejen en el dashboard. |

## Criterios de aceptación
- El Dashboard muestra correctamente el saldo neto y totales del mes en curso.
- Se pueden crear presupuestos para categorías existentes y se validan montos > 0.
- La barra de progreso de presupuestos cambia de color/estado según el porcentaje consumido (Normal, Advertencia, Excedido).
- Los reportes reflejan la distribución real de gastos almacenados en SQLite.
- Todas las pantallas cumplen con la paleta de colores y tipografía de `DESIGN.md`.

## Registro de avances

| Fecha | Cambio realizado | Archivos afectados | Avance actualizado |
|---|---|---|---|
| 13/05/2024 | Creación del plan de trabajo inicial para el Desarrollador 4. | `docs/Fase_4_presupuestos_dashboard_reportes_desarrollador_4_plan.md` | 5% |
| 14/05/2024 | Implementación de T02: DashboardActivity y su layout. | `activity_dashboard.xml`, `DashboardActivity.kt`, `strings.xml`, `dimens.xml` | 20% |
| 15/05/2024 | Implementación de T03: DashboardController y desacoplamiento de lógica. | `DashboardController.kt`, `DashboardActivity.kt` | 30% |
| 16/05/2024 | Implementación de T04, T05 y T07: Módulo de Presupuestos completo. | `activity_presupuestos.xml`, `PresupuestoActivity.kt`, `PresupuestoAdapter.kt`, `PresupuestoController.kt` | 60% |
| 17/05/2024 | Implementación de T06: Lógica de Alertas automáticas en Dashboard. | `DashboardController.kt`, `DashboardActivity.kt` | 70% |
| 18/05/2024 | Implementación de T08: Módulo de Reportes con distribución por categoría. | `activity_reportes.xml`, `ReportesActivity.kt`, `ReporteCategoriaAdapter.kt` | 85% |
| 19/05/2024 | Implementación de T09: Refuerzo de seguridad con filtrado por UID. | `PresupuestoController.kt`, `PresupuestoActivity.kt` | 95% |

## Resultado esperado
Un sistema de monitoreo financiero funcional que proporcione al estudiante universitario una visión clara de sus finanzas, ayudándole a no exceder sus límites de gasto mediante alertas visuales y reportes comprensibles.
