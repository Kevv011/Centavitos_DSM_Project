# Plan de trabajo — Autenticación, sesión y perfil

## Responsable
- Desarrollador: Desarrollador 1 (Persona 1)
- Responsabilidad: Autenticación de usuarios y gestión de perfil
- Estado general: Completado
- Porcentaje de avance: 100%

## Objetivo
Implementar un sistema seguro de autenticación utilizando Firebase Authentication y permitir la gestión de un perfil de usuario persistido localmente en SQLite, asegurando que cada usuario tenga una experiencia personalizada y segura.

## Alcance
- Integración de Firebase Authentication (Correo/Contraseña). [Completado]
- Flujos de Registro, Login, Recuperación de contraseña y Cierre de sesión. [Completado]
- Pantalla de Perfil para edición de datos personales (Nombre, Apellido, Carrera, Moneda). [Completado]
- Persistencia de datos de perfil en SQLite asociados al `firebase_uid`. [Completado]
- Validaciones de formularios y manejo de errores. [Completado]
- Protección de rutas (redirección si no hay sesión). [Completado]

## Dependencias
- **Desarrollador 2 (SQLite):** Estructura base de `HelperDB.kt` creada para soportar el perfil.
- **Desarrollador 5 (Diseño/UI):** Estilos de `DESIGN.md` aplicados en Login, Registro y Perfil.

## Archivos involucrados
- `ui/LoginActivity.kt`, `layout/activity_login.xml`
- `ui/RegisterActivity.kt`, `layout/activity_register.xml`
- `ui/ForgotPasswordActivity.kt`, `layout/activity_forgot_password.xml`
- `ui/PerfilActivity.kt`, `layout/activity_perfil.xml`
- `controller/AuthController.kt`, `controller/PerfilController.kt`
- `model/Usuario.kt`
- `db/HelperDB.kt`
- `google-services.json` (Firebase config)

## Tareas ejecutables

| ID | Tarea | Estado | Porcentaje | Observaciones |
|---|---|---|---:|---|
| T01 | Configuración de Firebase en el proyecto (Console + SDK) | Completado | 100% | Archivo google-services.json verificado y SDK configurado |
| T02 | Implementación de `AuthController` para lógica de Firebase | Completado | 100% | Métodos login, register, logout y recoverPassword implementados |
| T03 | Creación de `LoginActivity` y su Layout (Estilo Mintlify) | Completado | 100% | Diseño funcional con View Binding y estilos de DESIGN.md |
| T04 | Creación de `RegisterActivity` y su Layout | Completado | 100% | Flujo de registro funcional con validaciones |
| T05 | Creación de `ForgotPasswordActivity` y su Layout | Completado | 100% | Flujo de recuperación funcional |
| T06 | Implementación de `PerfilActivity` y Layout de edición | Completado | 100% | Pantalla de perfil creada con ScrollView y campos extendidos |
| T07 | Integración de Perfil con SQLite (vía `HelperDB`) | Completado | 100% | Uso de PerfilController para CRUD local asociado al UID |
| T08 | Gestión de sesión (Redirección al Dashboard si está logueado) | Completado | 100% | Verificación en LoginActivity |

## Criterios de aceptación
- El usuario puede registrarse y los datos aparecen en la consola de Firebase. [OK]
- El usuario puede iniciar sesión y es redirigido al Dashboard. [OK]
- El `firebase_uid` se obtiene correctamente tras el login. [OK]
- El perfil se guarda en SQLite y persiste tras cerrar la app. [OK]
- Se muestran mensajes de error claros. [OK]
- El diseño sigue la paleta de colores y componentes definidos en `DESIGN.md`. [OK]

## Registro de avances

| Fecha | Cambio realizado | Archivos afectados | Avance actualizado |
|---|---|---|---|
| 2024-05-22 | Creación del plan de trabajo inicial | `docs/Fase_1_autenticacion_perfil_desarrollador_1_plan.md` | 0% |
| 2024-05-22 | Configuración de dependencias de Firebase y View Binding | `app/build.gradle.kts`, `gradle/libs.versions.toml` | 10% |
| 2024-05-22 | Verificación de google-services.json y cierre de T01 | `app/google-services.json` | 15% |
| 2024-05-22 | Implementación de AuthController y flujos Auth (Login, Registro, Recuperación) | `AuthController.kt`, `LoginActivity.kt`, `RegisterActivity.kt`, `ForgotPasswordActivity.kt` | 60% |
| 2024-05-22 | Implementación de Perfil, PerfilController y HelperDB base | `PerfilActivity.kt`, `PerfilController.kt`, `HelperDB.kt`, `activity_perfil.xml` | 100% |

## Resultado esperado
Un módulo de autenticación robusto y una gestión de perfil funcional que sirva como puerta de entrada segura a la aplicación, proporcionando el identificador único (`uid`) necesario para el resto de los módulos.
