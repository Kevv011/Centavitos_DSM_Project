# Plan de trabajo — API REST, Retrofit, Recursos Visuales e Integración UI

## Responsable
- Desarrollador: Desarrollador 5
- Responsabilidad: API REST, Retrofit, recursos visuales e integración UI
- Estado general: En planeación
- Porcentaje de avance: 80%

## Objetivo
Implementar el consumo de una API REST mediante Retrofit para mostrar noticias o consejos financieros y asegurar que la interfaz de usuario de toda la aplicación cumpla con los lineamientos visuales definidos en DESIGN.md.

## Alcance
- Configuración de la librería Retrofit y GSON.
- Creación de la interfaz de servicios API y el cliente Retrofit.
- Implementación del modelo de datos para noticias/consejos.
- Desarrollo de la pantalla de noticias con RecyclerView.
- Gestión de estados de carga, error y lista vacía.
- Integración de recursos visuales (iconos, logos, imágenes).
- Aplicación de estilos, colores y tipografía según DESIGN.md en todo el proyecto.

## Dependencias
- **Desarrollador 1:** Obtención de `firebase_uid` para posibles personalizaciones y acceso a la app.
- **Desarrollador 2:** Coordinación para asegurar que los modelos de la API no colisionen con los de SQLite.
- **General:** Todos los desarrolladores dependen de las definiciones visuales y recursos que el Desarrollador 5 organice.

## Archivos involucrados
- `network/RetrofitClient.kt`: Cliente base para Retrofit.
- `network/ApiService.kt`: Interfaz con los endpoints de la API.
- `model/Noticia.kt`: Modelo de datos para las noticias.
- `controller/NoticiaController.kt`: Lógica de negocio para el manejo de noticias.
- `adapter/NoticiaAdapter.kt`: Adaptador para el RecyclerView de noticias.
- `ui/NoticiasActivity.kt`: Pantalla de visualización de noticias.
- `res/layout/activity_noticias.xml`: Layout de la pantalla de noticias.
- `res/layout/item_noticia.xml`: Layout para cada ítem de noticia.
- `res/values/colors.xml`: Definición de paleta de colores de DESIGN.md.
- `res/values/themes.xml`: Definición de estilos globales.

## Tareas ejecutables

| ID | Tarea | Estado | Porcentaje | Observaciones |
|---|---|---|---:|---|
| T01 | Configuración de dependencias (Retrofit, Gson, Glide/Picasso) | Finalizado | 100% | |
| T02 | Implementación de `RetrofitClient` y `ApiService` | Finalizado | 100% | |
| T03 | Creación del modelo `Noticia` y `NoticiaController` | Finalizado | 100% | |
| T04 | Diseño y creación de `activity_noticias.xml` e `item_noticia.xml` | Finalizado | 100% | |
| T05 | Implementación de `NoticiaAdapter` y `NoticiasActivity` | Finalizado | 100% | |
| T06 | Integración de lógica para abrir artículos en navegador externo | Finalizado | 100% | |
| T07 | Manejo de errores de conexión y estados de carga | Finalizado | 100% | |
| T08 | Configuración de colores, tipografía y estilos en `res/values` | Finalizado | 100% | |
| T09 | Organización e integración de iconos y logotipos en `drawable` | Pendiente | 0% | |
| T10 | Revisión y ajuste de UI en todas las pantallas según DESIGN.md | Finalizado | 100% | |

## Criterios de aceptación
- La aplicación consume datos reales de una API REST.
- Las noticias se muestran correctamente en un RecyclerView.
- Al hacer clic en una noticia, se abre el navegador con la URL correspondiente.
- Se muestran mensajes claros en caso de error de red.
- La paleta de colores y estilos en toda la app coinciden con DESIGN.md.
- Los recursos visuales están correctamente escalados y organizados.

## Registro de avances

| Fecha | Cambio realizado | Archivos afectados | Avance actualizado |
|---|---|---|---|
| 2024-05-22 | Creación del plan de trabajo inicial | `docs/Fase_5_api_rest_design_integracion_desarrollador_5_plan.md` | 0% |
| 2024-05-22 | Implementación completa del módulo de noticias (API, Modelos, UI) y ajuste de temas visuales | `activity_noticias.xml`, `item_noticia.xml`, `NoticiaAdapter.kt`, `NoticiasActivity.kt`, `themes.xml`, `colors.xml` | 80% |

## Resultado esperado
Un módulo de noticias funcional e integrado que sirve como fuente de información financiera para el usuario, junto con una base visual coherente y profesional aplicada a toda la aplicación DSM_Centavitos.
