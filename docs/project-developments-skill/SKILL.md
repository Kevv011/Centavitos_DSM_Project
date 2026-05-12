# SKILL.md — Desarrollo de App Móvil de Finanzas Personales

## 1. Propósito del proyecto

Desarrollar una aplicación móvil Android orientada a estudiantes universitarios para facilitar la gestión de sus finanzas personales. La app permitirá registrar, organizar, consultar y analizar ingresos y gastos, establecer presupuestos por categoría, recibir alertas cuando se acerquen o excedan límites definidos, visualizar información financiera resumida en un dashboard y consumir contenido externo mediante una API REST.

El proyecto se desarrollará con **Kotlin en Android Studio**, utilizando **interfaces XML con Empty Views Activity**, **Firebase Authentication** para registro e inicio de sesión, **SQLite** para persistencia local de datos, **Retrofit** para consumo de API REST y una organización basada en **arquitectura MVC**.

El diseño visual de la aplicación será definido en un archivo separado llamado **DESIGN.md**, donde se documentarán los lineamientos de UI/UX, colores, tipografía, espaciados, iconografía, imágenes, logotipo y criterios visuales generales.

---

## 2. Enfoque general de la aplicación

La aplicación busca resolver el problema de control financiero personal en estudiantes universitarios, proporcionando una herramienta sencilla, intuitiva y organizada para:

- Registrar ingresos y gastos.
- Clasificar movimientos por categorías.
- Establecer presupuestos mensuales.
- Recibir alertas de consumo excesivo.
- Consultar historial financiero.
- Filtrar movimientos.
- Visualizar resumen financiero mensual.
- Consumir noticias, consejos o artículos financieros desde una API REST.
- Mantener la información persistente en SQLite.
- Controlar el acceso mediante Firebase Authentication.

La experiencia de usuario debe ser clara, rápida y comprensible, evitando pantallas saturadas o flujos innecesariamente complejos.

---

## 3. Tecnologías principales

| Área | Tecnología |
|---|---|
| Lenguaje | Kotlin |
| IDE | Android Studio |
| Tipo de interfaz | XML con Empty Views Activity |
| Acceso a vistas | View Binding |
| Autenticación | Firebase Authentication |
| Base de datos local | SQLite con SQLiteOpenHelper |
| Consumo de API REST | Retrofit |
| Conversión JSON | Gson Converter |
| Cliente HTTP base | OkHttp, usado internamente por Retrofit |
| Arquitectura | MVC |
| Listas dinámicas | RecyclerView + Adapter |
| Navegación | Activities e Intents |
| Recursos visuales | Drawable, mipmap, iconos e imágenes locales |
| Diseño visual | Definido en DESIGN.md |

---

## 4. Justificación técnica de la elección de XML

El proyecto se desarrollará usando **Empty Views Activity con XML**, ya que las guías de aprendizaje base trabajan con este enfoque y permiten integrar de forma más directa:

- Firebase Authentication.
- SQLiteOpenHelper.
- Retrofit.
- RecyclerView.
- View Binding.
- Activities.
- Intents.
- MVC.

Aunque Jetpack Compose es una alternativa moderna, para este proyecto se prioriza la coherencia con las guías académicas, la facilidad de implementación y la reducción de riesgo técnico. XML permite aplicar el patrón MVC de forma clara, separando las vistas en archivos `.xml`, la lógica de control en Activities o controladores, y los datos en modelos.

---

## 5. Arquitectura general propuesta

El proyecto seguirá una estructura inspirada en MVC:

```text
app/
├── db/
│   └── HelperDB.kt
│
├── model/
│   ├── Usuario.kt
│   ├── Categoria.kt
│   ├── Movimiento.kt
│   ├── Presupuesto.kt
│   ├── Alerta.kt
│   ├── MetodoPago.kt
│   ├── Etiqueta.kt
│   └── Noticia.kt
│
├── controller/
│   ├── AuthController.kt
│   ├── PerfilController.kt
│   ├── MovimientoController.kt
│   ├── PresupuestoController.kt
│   ├── DashboardController.kt
│   ├── HistorialController.kt
│   └── NoticiaController.kt
│
├── network/
│   ├── ApiService.kt
│   └── RetrofitClient.kt
│
├── adapter/
│   ├── MovimientoAdapter.kt
│   ├── PresupuestoAdapter.kt
│   ├── CategoriaAdapter.kt
│   └── NoticiaAdapter.kt
│
├── ui/
│   ├── LoginActivity.kt
│   ├── RegisterActivity.kt
│   ├── ForgotPasswordActivity.kt
│   ├── DashboardActivity.kt
│   ├── PerfilActivity.kt
│   ├── MovimientoFormActivity.kt
│   ├── HistorialActivity.kt
│   ├── PresupuestoActivity.kt
│   ├── ReportesActivity.kt
│   └── NoticiasActivity.kt
│
└── res/
    ├── layout/
    ├── drawable/
    ├── mipmap/
    ├── values/
    └── navigation/
```

---

## 6. Relación con arquitectura MVC

### 6.1 Modelo

El modelo representa las entidades principales de la aplicación y sus datos.

Modelos principales:

- `Usuario`
- `Categoria`
- `Movimiento`
- `Presupuesto`
- `Alerta`
- `MetodoPago`
- `Etiqueta`
- `Noticia`

Estos modelos deben representar los datos usados en SQLite, Firebase Authentication y Retrofit.

### 6.2 Vista

La vista estará formada por los archivos XML ubicados en `res/layout`.

Ejemplos:

- `activity_login.xml`
- `activity_register.xml`
- `activity_dashboard.xml`
- `activity_movimiento_form.xml`
- `activity_historial.xml`
- `activity_presupuestos.xml`
- `activity_noticias.xml`
- `item_movimiento.xml`
- `item_noticia.xml`
- `item_presupuesto.xml`

La vista no debe contener lógica de negocio. Su responsabilidad será mostrar la información e interactuar con el usuario.

### 6.3 Controlador

Los controladores y Activities coordinarán las acciones del usuario, validaciones, llamadas a SQLite, Firebase o Retrofit, y actualización de la vista.

Ejemplos:

- `AuthController`: registro, login, cierre de sesión y recuperación de contraseña.
- `MovimientoController`: creación, edición, eliminación y consulta de movimientos.
- `PresupuestoController`: gestión de presupuestos y cálculo de alertas.
- `HistorialController`: filtros y búsquedas.
- `DashboardController`: cálculo de resumen financiero.
- `NoticiaController`: consumo de API REST.

---

## 7. Requerimientos funcionales específicos

### RF01 — Registro de estudiante

La aplicación debe permitir registrar estudiantes mediante correo electrónico y contraseña usando Firebase Authentication.

Datos mínimos:

- Correo electrónico.
- Contraseña.
- Confirmación de contraseña.

Validaciones:

- Correo obligatorio.
- Formato de correo válido.
- Contraseña obligatoria.
- Contraseña con longitud mínima aceptable.
- Confirmación de contraseña coincidente.
- Manejo de correo ya registrado.
- Manejo de errores de Firebase.

Resultado esperado:

- Usuario creado en Firebase Authentication.
- Redirección al dashboard o pantalla de perfil inicial.
- Registro del `firebase_uid` para asociar datos locales.

---

### RF02 — Inicio de sesión

La aplicación debe permitir iniciar sesión con correo y contraseña mediante Firebase Authentication.

Validaciones:

- Correo obligatorio.
- Contraseña obligatoria.
- Credenciales inválidas.
- Usuario inexistente.
- Error de conexión.

Resultado esperado:

- Usuario autenticado.
- Sesión activa.
- Redirección al dashboard.
- Consulta de datos locales asociados al `firebase_uid`.

---

### RF03 — Recuperación o cambio de contraseña

La aplicación debe permitir al usuario recuperar o cambiar su contraseña mediante Firebase.

Resultado esperado:

- Envío de correo de recuperación o flujo equivalente.
- Mensaje visual de confirmación.
- Manejo de errores cuando el correo no exista o no sea válido.

---

### RF04 — Perfil del estudiante

La aplicación debe permitir gestionar datos personales y académicos del estudiante.

Datos sugeridos:

- Nombre.
- Apellido.
- Correo.
- Carrera o área académica.
- Moneda local.
- Imagen o avatar opcional.

Resultado esperado:

- Perfil asociado al usuario autenticado.
- Datos guardados localmente en SQLite.
- Posibilidad de editar información.

---

### RF05 — Gestión de ingresos y gastos

La aplicación debe permitir registrar movimientos financieros de tipo ingreso o gasto.

Campos requeridos:

- Tipo de movimiento: ingreso o gasto.
- Monto.
- Categoría.
- Fecha.
- Método de pago opcional.
- Descripción.
- Etiquetas personalizadas opcionales.
- Usuario asociado mediante `firebase_uid`.

Operaciones requeridas:

- Crear movimiento.
- Editar movimiento.
- Eliminar movimiento.
- Consultar movimientos.
- Validar datos antes de guardar.

Resultado esperado:

- Movimientos persistidos en SQLite.
- Datos disponibles para dashboard, historial, presupuestos y reportes.

---

### RF06 — Categorías

La aplicación debe manejar categorías para clasificar ingresos y gastos.

Categorías sugeridas:

- Alimentación.
- Transporte.
- Ocio.
- Servicios.
- Educación.
- Salud.
- Salario.
- Beca.
- Otros.

Resultado esperado:

- Categorías precargadas al iniciar la app por primera vez.
- Categorías disponibles en formularios mediante Spinner o selector.
- Relación entre movimientos y categorías.

---

### RF07 — Presupuestos mensuales

La aplicación debe permitir establecer presupuestos mensuales por categoría de gasto.

Campos sugeridos:

- Categoría.
- Monto límite.
- Mes.
- Año.
- Usuario asociado.

Validaciones:

- Monto mayor a cero.
- Categoría obligatoria.
- Evitar presupuestos duplicados para la misma categoría, mes y usuario.

Resultado esperado:

- Presupuesto guardado en SQLite.
- Disponible para comparación contra gastos reales.

---

### RF08 — Alertas de presupuesto

La aplicación debe generar alertas visuales cuando el gasto se acerque al límite o lo exceda.

Estados sugeridos:

- Normal: gasto bajo control.
- Advertencia: gasto cercano al límite.
- Excedido: presupuesto superado.

Resultado esperado:

- Alertas visibles en dashboard y módulo de presupuestos.
- Mensajes claros para el usuario.
- Cálculo basado en gastos acumulados por categoría, mes y usuario.

---

### RF09 — Historial financiero

La aplicación debe mostrar el historial completo de ingresos y gastos.

Debe permitir:

- Ver todos los movimientos.
- Buscar por texto.
- Filtrar por fecha.
- Filtrar por categoría.
- Filtrar por tipo: ingreso o gasto.
- Filtrar por monto mínimo y máximo.
- Acceder a edición o eliminación de un movimiento.

Resultado esperado:

- Historial claro, ordenado y filtrable.
- Uso de RecyclerView.
- Consultas eficientes en SQLite.

---

### RF10 — Dashboard principal

La aplicación debe mostrar una pantalla inicial con resumen financiero.

Elementos mínimos:

- Saldo disponible.
- Total de ingresos del mes.
- Total de gastos del mes.
- Presupuestos excedidos.
- Alertas importantes.
- Accesos rápidos para agregar movimiento.
- Acceso a historial.
- Acceso a reportes.
- Acceso a noticias.

Resultado esperado:

- Resumen financiero rápido.
- Información calculada desde SQLite.
- Experiencia clara y visualmente intuitiva.

---

### RF11 — Reportes gráficos

La aplicación debe mostrar reportes visuales simples y comprensibles.

Reportes sugeridos:

- Gastos por categoría.
- Comparación ingresos vs gastos.
- Evolución mensual.
- Categorías con mayor gasto.

Resultado esperado:

- Reportes claros.
- Información calculada desde movimientos almacenados.
- Visualización útil para toma de decisiones.

---

### RF12 — Noticias, consejos o artículos mediante API REST

La aplicación debe consumir una API REST pública o propia para mostrar contenido financiero.

Datos mínimos por noticia:

- Título.
- Resumen.
- Fecha.
- URL del artículo.
- Imagen opcional.

Resultado esperado:

- Listado de noticias usando RecyclerView.
- Consumo mediante Retrofit.
- Conversión JSON mediante Gson.
- Apertura del artículo completo en navegador externo.
- Manejo de estados de carga, error y lista vacía.

---

## 8. Requerimientos no funcionales

### RNF01 — Usabilidad

La aplicación debe ser intuitiva, clara y fácil de usar para estudiantes universitarios.

### RNF02 — Persistencia

Los datos financieros deben mantenerse almacenados localmente en SQLite, incluso si la app se cierra.

### RNF03 — Seguridad de acceso

El acceso a la app debe estar protegido mediante Firebase Authentication.

### RNF04 — Separación de responsabilidades

El código debe organizarse siguiendo MVC, evitando concentrar toda la lógica en una sola Activity.

### RNF05 — Manejo de errores

La app debe mostrar mensajes comprensibles ante errores de autenticación, validación, base de datos o conexión.

### RNF06 — Rendimiento

Las consultas a SQLite y consumo de API deben evitar bloquear la interfaz de usuario.

### RNF07 — Mantenibilidad

El proyecto debe mantener nombres claros, paquetes organizados y responsabilidades separadas.

### RNF08 — Diseño visual

La parte visual debe seguir los lineamientos definidos en `DESIGN.md`.

---

## 9. Lineamientos técnicos basados en las guías

### 9.1 Firebase Authentication

Se utilizará Firebase Authentication para:

- Registrar usuarios.
- Iniciar sesión.
- Mantener sesión activa.
- Recuperar contraseña.
- Obtener el usuario actual.
- Obtener el `uid` del usuario autenticado.

El `uid` será clave para asociar los datos locales en SQLite al usuario correcto.

Flujo base:

```text
RegisterActivity/LoginActivity
        ↓
FirebaseAuth
        ↓
Usuario autenticado
        ↓
Obtención de UID
        ↓
DashboardActivity
        ↓
Consulta de datos en SQLite por UID
```

---

### 9.2 SQLite

Se utilizará SQLite mediante `SQLiteOpenHelper`.

Componentes principales:

- `HelperDB.kt`
- Modelos con sentencias `CREATE TABLE`.
- Métodos CRUD.
- `ContentValues` para inserción y actualización.
- `Cursor` para consultas.
- `getWritableDatabase()` y `getReadableDatabase()` para acceso a base de datos.

La base de datos debe almacenar información financiera local asociada al usuario autenticado.

---

### 9.3 Retrofit

Se utilizará Retrofit para consumir API REST.

Componentes principales:

- `ApiService.kt`
- `RetrofitClient.kt`
- Modelos de respuesta.
- Anotaciones `@GET`, `@POST`, `@PUT`, `@DELETE`, `@Path`, `@Body`.
- Gson Converter para convertir JSON a objetos Kotlin.

Para este proyecto, Retrofit se usará principalmente en el módulo de noticias o consejos financieros.

---

### 9.4 HTTP Request

La app deberá comprender el flujo cliente-servidor:

```text
Aplicación Android
        ↓
HTTP Request
        ↓
API REST
        ↓
HTTP Response
        ↓
JSON
        ↓
Objeto Kotlin
        ↓
Vista en RecyclerView
```

Métodos HTTP relevantes:

- `GET`: consultar noticias o recursos.
- `POST`: crear recursos si se usa API propia.
- `PUT`: actualizar recursos si se usa API propia.
- `DELETE`: eliminar recursos si se usa API propia.

---

### 9.5 View Binding

Se recomienda habilitar View Binding para reducir errores al acceder a vistas XML.

Ventajas:

- Acceso más seguro a vistas.
- Menos uso de `findViewById`.
- Mejor legibilidad.
- Menor riesgo de errores por IDs incorrectos.

---

### 9.6 RecyclerView

Se utilizará RecyclerView para mostrar listas dinámicas:

- Movimientos financieros.
- Presupuestos.
- Categorías.
- Noticias.
- Alertas.
- Reportes resumidos.

Cada RecyclerView deberá tener:

- Layout de item.
- Adapter.
- ViewHolder.
- Lista de datos.
- Eventos de clic si aplica.

---

## 10. Diseño visual y recursos gráficos

El diseño visual será documentado en un archivo independiente llamado **DESIGN.md**.

Ese archivo deberá contener:

- Paleta de colores.
- Tipografía.
- Tamaños de texto.
- Estilos de botones.
- Estilos de formularios.
- Espaciados.
- Iconografía.
- Uso de logotipo principal.
- Uso de imágenes.
- Estilo de tarjetas.
- Estilo de dashboard.
- Estados visuales: éxito, advertencia, error, vacío y carga.
- Lineamientos de accesibilidad visual.

El proyecto podrá incluir recursos visuales como:

- Logotipo principal.
- Iconos para navegación.
- Iconos para categorías.
- Imágenes para noticias.
- Ilustraciones o elementos decorativos.
- Recursos en `drawable` y `mipmap`.

El archivo `SKILL.md` define el desarrollo técnico. El archivo `DESIGN.md` definirá la experiencia visual.

---

## 11. Modelo de datos sugerido

### 11.1 Tabla usuarios_locales

Propósito: guardar información extendida del usuario autenticado.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
firebase_uid TEXT NOT NULL UNIQUE
nombre TEXT
apellido TEXT
correo TEXT NOT NULL
carrera TEXT
moneda TEXT
avatar_path TEXT
created_at TEXT
updated_at TEXT
```

---

### 11.2 Tabla categorias

Propósito: clasificar ingresos y gastos.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
nombre TEXT NOT NULL
tipo TEXT NOT NULL
icono TEXT
color TEXT
firebase_uid TEXT
```

Notas:

- `tipo` puede ser `INGRESO` o `GASTO`.
- Algunas categorías pueden ser globales o precargadas.

---

### 11.3 Tabla movimientos

Propósito: guardar ingresos y gastos.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
firebase_uid TEXT NOT NULL
tipo TEXT NOT NULL
monto REAL NOT NULL
categoria_id INTEGER NOT NULL
fecha TEXT NOT NULL
metodo_pago TEXT
descripcion TEXT
created_at TEXT
updated_at TEXT
FOREIGN KEY(categoria_id) REFERENCES categorias(id)
```

---

### 11.4 Tabla presupuestos

Propósito: guardar límites mensuales por categoría.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
firebase_uid TEXT NOT NULL
categoria_id INTEGER NOT NULL
monto_limite REAL NOT NULL
mes INTEGER NOT NULL
anio INTEGER NOT NULL
created_at TEXT
updated_at TEXT
FOREIGN KEY(categoria_id) REFERENCES categorias(id)
```

---

### 11.5 Tabla etiquetas

Propósito: manejar etiquetas personalizadas.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
firebase_uid TEXT NOT NULL
nombre TEXT NOT NULL
```

---

### 11.6 Tabla movimiento_etiqueta

Propósito: relacionar movimientos con etiquetas.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
movimiento_id INTEGER NOT NULL
etiqueta_id INTEGER NOT NULL
FOREIGN KEY(movimiento_id) REFERENCES movimientos(id)
FOREIGN KEY(etiqueta_id) REFERENCES etiquetas(id)
```

---

### 11.7 Tabla alertas

Propósito: registrar alertas importantes.

Campos sugeridos:

```text
id INTEGER PRIMARY KEY AUTOINCREMENT
firebase_uid TEXT NOT NULL
presupuesto_id INTEGER
tipo TEXT NOT NULL
mensaje TEXT NOT NULL
fecha TEXT NOT NULL
estado TEXT NOT NULL
FOREIGN KEY(presupuesto_id) REFERENCES presupuestos(id)
```

---

## 12. Fases de desarrollo

## Fase 1 — Análisis, planificación y diseño funcional

Objetivo:

Definir el alcance técnico y funcional del proyecto.

Tareas:

- Analizar la rúbrica del proyecto.
- Identificar módulos obligatorios.
- Definir flujo general de navegación.
- Definir arquitectura MVC.
- Definir entidades principales.
- Definir responsabilidades técnicas.
- Crear `SKILL.md`.
- Crear `DESIGN.md`.
- Crear mockups o vistas iniciales sin funcionamiento.

Resultado esperado:

- Documentación técnica base.
- Diseño visual documentado.
- Flujo de trabajo claro.
- Pantallas planificadas.

---

## Fase 2 — Configuración inicial del proyecto Android

Objetivo:

Crear la base del proyecto en Android Studio.

Tareas:

- Crear proyecto con Empty Views Activity.
- Configurar Kotlin.
- Configurar Minimum SDK.
- Habilitar View Binding.
- Configurar estructura de paquetes.
- Agregar recursos iniciales.
- Agregar logotipo e iconos base.
- Configurar permisos de internet.
- Preparar navegación inicial entre Activities.

Resultado esperado:

- Proyecto Android funcional.
- Estructura inicial organizada.
- Base lista para integrar Firebase, SQLite y Retrofit.

---

## Fase 3 — Implementación de Firebase Authentication

Objetivo:

Implementar registro, inicio de sesión y sesión de usuario.

Tareas:

- Crear proyecto en Firebase.
- Habilitar Authentication con correo y contraseña.
- Conectar Android Studio con Firebase.
- Agregar `google-services.json`.
- Agregar dependencias de Firebase.
- Crear `RegisterActivity`.
- Crear `LoginActivity`.
- Crear `ForgotPasswordActivity`.
- Implementar registro.
- Implementar login.
- Implementar cierre de sesión.
- Obtener `firebase_uid`.
- Manejar errores de Firebase.

Resultado esperado:

- Usuario puede registrarse.
- Usuario puede iniciar sesión.
- Usuario puede recuperar contraseña.
- App identifica al usuario autenticado.
- UID disponible para SQLite.

---

## Fase 4 — Implementación de SQLite

Objetivo:

Crear la base de datos local y persistir información financiera.

Tareas:

- Crear paquete `db`.
- Crear `HelperDB.kt`.
- Definir tablas.
- Definir modelos.
- Crear métodos CRUD.
- Insertar categorías por defecto.
- Asociar registros al `firebase_uid`.
- Validar creación y actualización de base.
- Implementar consultas básicas.

Resultado esperado:

- Base de datos local funcionando.
- Tablas creadas correctamente.
- Datos persistentes por usuario.
- Categorías precargadas.

---

## Fase 5 — Gestión de ingresos y gastos

Objetivo:

Implementar el módulo principal de movimientos financieros.

Tareas:

- Crear formulario de movimiento.
- Crear selector de tipo: ingreso/gasto.
- Crear selector de categoría.
- Capturar monto, fecha, método de pago, descripción y etiquetas.
- Validar campos.
- Guardar movimientos en SQLite.
- Editar movimientos.
- Eliminar movimientos.
- Mostrar movimientos en RecyclerView.

Resultado esperado:

- CRUD completo de movimientos financieros.
- Datos disponibles para cálculos y consultas.
- Lista de movimientos funcional.

---

## Fase 6 — Presupuestos y alertas

Objetivo:

Controlar presupuestos mensuales por categoría.

Tareas:

- Crear formulario de presupuesto.
- Validar categoría, mes, año y monto.
- Guardar presupuesto en SQLite.
- Consultar gastos por categoría y mes.
- Comparar gasto real contra presupuesto.
- Generar estados de alerta.
- Mostrar alertas visuales.
- Mostrar presupuestos en RecyclerView.

Resultado esperado:

- Presupuestos por categoría funcionando.
- Alertas generadas correctamente.
- Usuario puede saber cuándo se acerca o excede un límite.

---

## Fase 7 — Historial financiero y filtros

Objetivo:

Permitir consulta avanzada de movimientos financieros.

Tareas:

- Crear pantalla de historial.
- Mostrar lista de movimientos.
- Implementar búsqueda por texto.
- Implementar filtro por fecha.
- Implementar filtro por categoría.
- Implementar filtro por tipo.
- Implementar filtro por monto mínimo y máximo.
- Permitir editar o eliminar desde historial.

Resultado esperado:

- Historial financiero claro.
- Filtros funcionales.
- Búsquedas eficientes.

---

## Fase 8 — Dashboard principal

Objetivo:

Mostrar resumen financiero inicial.

Tareas:

- Calcular total de ingresos del mes.
- Calcular total de gastos del mes.
- Calcular saldo disponible.
- Mostrar alertas importantes.
- Mostrar presupuestos excedidos.
- Crear accesos rápidos.
- Conectar dashboard con movimientos, presupuestos, historial y reportes.

Resultado esperado:

- Pantalla principal útil y clara.
- Resumen financiero actualizado.
- Navegación rápida a módulos importantes.

---

## Fase 9 — Consumo de API REST con Retrofit

Objetivo:

Integrar API REST para mostrar noticias, consejos o artículos financieros.

Tareas:

- Seleccionar API pública o propia.
- Crear modelos de datos.
- Crear `ApiService.kt`.
- Crear `RetrofitClient.kt`.
- Implementar petición GET.
- Convertir JSON a objetos Kotlin.
- Mostrar noticias en RecyclerView.
- Abrir artículo en navegador externo.
- Manejar errores de conexión.
- Mostrar estados de carga y lista vacía.

Resultado esperado:

- API REST consumida correctamente.
- Noticias o consejos visibles en la app.
- Módulo externo integrado con Retrofit.

---

## Fase 10 — Reportes y visualización de datos

Objetivo:

Mostrar análisis financiero visual y comprensible.

Tareas:

- Calcular gastos por categoría.
- Calcular ingresos vs gastos.
- Calcular evolución mensual.
- Crear vistas de reportes.
- Mostrar resultados de forma visual.
- Integrar diseño definido en `DESIGN.md`.

Resultado esperado:

- Reportes útiles.
- Información clara para el estudiante.
- Mejor comprensión de hábitos financieros.

---

## Fase 11 — Validaciones, errores y calidad

Objetivo:

Mejorar la estabilidad y experiencia de usuario.

Tareas:

- Validar formularios.
- Controlar campos vacíos.
- Controlar montos inválidos.
- Controlar fechas inválidas.
- Manejar errores de Firebase.
- Manejar errores de SQLite.
- Manejar errores de Retrofit.
- Crear mensajes de usuario claros.
- Revisar navegación.
- Probar flujos principales.

Resultado esperado:

- App más estable.
- Menos errores inesperados.
- Mejor experiencia de usuario.

---

## Fase 12 — Integración final, pruebas y entrega

Objetivo:

Unificar módulos y preparar la entrega final.

Tareas:

- Integrar todos los módulos.
- Revisar consistencia visual.
- Verificar cumplimiento de requisitos.
- Probar registro/login.
- Probar CRUD de movimientos.
- Probar presupuestos y alertas.
- Probar historial y filtros.
- Probar dashboard.
- Probar API REST.
- Revisar documentación.
- Preparar presentación o evidencia.

Resultado esperado:

- Aplicación funcional.
- Documentación completa.
- Proyecto listo para evaluación.

---

## 13. Tareas principales por módulo

### Autenticación

- Crear pantallas de registro y login.
- Conectar Firebase.
- Validar credenciales.
- Manejar errores.
- Obtener UID.
- Cerrar sesión.

### Perfil

- Crear pantalla de perfil.
- Guardar datos extendidos en SQLite.
- Permitir edición.
- Gestionar moneda local.
- Mostrar correo autenticado.

### Movimientos

- Crear formulario.
- Guardar ingreso/gasto.
- Editar registro.
- Eliminar registro.
- Listar movimientos.
- Relacionar movimiento con categoría y usuario.

### Presupuestos

- Crear presupuesto mensual.
- Calcular gasto acumulado.
- Comparar contra límite.
- Mostrar estado.
- Generar alertas.

### Historial

- Listar todos los movimientos.
- Buscar por texto.
- Filtrar por categoría, fecha, tipo y monto.
- Permitir acciones rápidas.

### Dashboard

- Calcular saldo.
- Mostrar ingresos/gastos.
- Mostrar alertas.
- Accesos rápidos.
- Resumen mensual.

### Noticias/API REST

- Configurar Retrofit.
- Crear modelo de noticia.
- Consumir endpoint.
- Mostrar listado.
- Abrir artículo externo.
- Manejar errores.

---

## 14. Criterios de calidad

El proyecto debe cumplir con los siguientes criterios:

- Código organizado por paquetes.
- Separación MVC clara.
- Interfaces intuitivas.
- Formularios validados.
- Datos persistentes.
- Autenticación funcional.
- API REST integrada.
- Manejo de errores.
- Navegación fluida.
- Uso correcto de recursos visuales.
- Diseño alineado a `DESIGN.md`.
- Base de datos asociada al usuario autenticado.
- Cumplimiento de los módulos solicitados por la rúbrica.

---

## 15. Resultado esperado del proyecto

Al finalizar el desarrollo, se espera una aplicación Android funcional que permita a estudiantes universitarios gestionar sus finanzas personales de forma sencilla y segura.

El usuario final podrá:

- Registrarse e iniciar sesión.
- Gestionar su perfil.
- Registrar ingresos y gastos.
- Clasificar movimientos.
- Establecer presupuestos.
- Recibir alertas.
- Consultar historial financiero.
- Filtrar información.
- Ver resumen en dashboard.
- Consultar noticias o consejos financieros desde una API REST.
- Mantener sus datos guardados localmente en SQLite.

Desde el punto de vista técnico, el proyecto deberá demostrar dominio de:

- Kotlin.
- Android Studio.
- XML layouts.
- View Binding.
- Firebase Authentication.
- SQLite.
- SQLiteOpenHelper.
- Retrofit.
- JSON.
- RecyclerView.
- MVC.
- Validaciones.
- Manejo de errores.
- Integración de recursos visuales.

---

## 16. División de responsabilidades para 5 desarrolladores

El proyecto será desarrollado por 5 integrantes. La división debe ser equilibrada, procurando que cada desarrollador tenga responsabilidades funcionales, técnicas y de integración.

---

### Desarrollador 1 — Autenticación, sesión y perfil

Responsabilidades:

- Configurar Firebase.
- Implementar registro.
- Implementar inicio de sesión.
- Implementar recuperación de contraseña.
- Implementar cierre de sesión.
- Obtener y manejar `firebase_uid`.
- Crear módulo de perfil.
- Guardar perfil extendido en SQLite.
- Validar formularios de autenticación.
- Coordinar seguridad de acceso.

Entregables:

- `RegisterActivity`
- `LoginActivity`
- `ForgotPasswordActivity`
- `PerfilActivity`
- `AuthController`
- Integración Firebase funcionando.
- UID disponible para el resto de módulos.

---

### Desarrollador 2 — SQLite, modelos y persistencia base

Responsabilidades:

- Crear estructura de base de datos.
- Crear `HelperDB.kt`.
- Definir tablas principales.
- Crear modelos.
- Crear métodos CRUD base.
- Insertar categorías por defecto.
- Asociar registros al `firebase_uid`.
- Asegurar integridad de datos.
- Apoyar a los demás desarrolladores con consultas SQL.

Entregables:

- `HelperDB.kt`
- Modelos principales.
- Tablas SQLite.
- CRUD base.
- Categorías precargadas.
- Documentación de estructura de base de datos.

---

### Desarrollador 3 — Movimientos financieros e historial

Responsabilidades:

- Implementar formulario de ingresos/gastos.
- Crear, editar y eliminar movimientos.
- Crear lista de movimientos con RecyclerView.
- Implementar historial financiero.
- Implementar filtros por texto, fecha, categoría, tipo y monto.
- Validar campos financieros.
- Conectar movimientos con SQLite.

Entregables:

- `MovimientoFormActivity`
- `HistorialActivity`
- `MovimientoController`
- `MovimientoAdapter`
- Layouts de movimientos e historial.
- CRUD completo de movimientos.
- Filtros funcionales.

---

### Desarrollador 4 — Presupuestos, alertas, dashboard y reportes

Responsabilidades:

- Implementar presupuestos por categoría.
- Calcular gastos acumulados por mes.
- Generar alertas de presupuesto.
- Implementar dashboard principal.
- Calcular saldo, ingresos y gastos.
- Implementar reportes visuales.
- Integrar datos desde SQLite.
- Mostrar estados financieros claros.

Entregables:

- `PresupuestoActivity`
- `DashboardActivity`
- `ReportesActivity`
- `PresupuestoController`
- `DashboardController`
- `PresupuestoAdapter`
- Alertas funcionales.
- Dashboard funcional.
- Reportes financieros.

---

### Desarrollador 5 — API REST, Retrofit, recursos visuales e integración UI

Responsabilidades:

- Configurar Retrofit.
- Crear `ApiService.kt`.
- Crear `RetrofitClient.kt`.
- Crear modelo de noticias.
- Consumir API REST.
- Mostrar noticias en RecyclerView.
- Abrir artículos en navegador externo.
- Manejar errores de conexión.
- Integrar recursos visuales.
- Coordinar la aplicación de `DESIGN.md`.
- Revisar consistencia visual entre pantallas.

Entregables:

- `NoticiasActivity`
- `NoticiaController`
- `ApiService.kt`
- `RetrofitClient.kt`
- `NoticiaAdapter`
- Módulo API REST funcional.
- Recursos visuales organizados.
- Integración visual alineada a `DESIGN.md`.

---

## 17. Consideraciones de integración entre desarrolladores

Para evitar conflictos durante el desarrollo:

- Usar nombres claros en paquetes, clases y layouts.
- Mantener ramas separadas si se usa Git.
- Definir contratos entre módulos.
- No modificar archivos de otro desarrollador sin coordinación.
- Documentar cambios importantes.
- Revisar que todos los datos usen `firebase_uid`.
- Unificar estilos visuales desde `DESIGN.md`.
- Realizar pruebas de integración periódicas.
- Evitar duplicar modelos o controladores.
- Mantener una estructura común de navegación.

---

## 18. Flujo general esperado de la aplicación

```text
Inicio de app
    ↓
¿Usuario autenticado?
    ├── No → Login / Registro / Recuperación
    └── Sí → Dashboard
                ↓
        ┌───────┼────────┬─────────────┬───────────┐
        ↓       ↓        ↓             ↓           ↓
   Movimientos Historial Presupuestos Reportes Noticias
        ↓       ↓        ↓             ↓           ↓
     SQLite  SQLite   SQLite       SQLite      Retrofit
```

---

## 19. Reglas técnicas del proyecto

- Usar Kotlin.
- Usar XML, no Jetpack Compose.
- Usar Empty Views Activity.
- Usar View Binding.
- Usar Firebase Authentication solo para autenticación.
- Usar SQLite para datos financieros.
- Usar Retrofit para API REST.
- Usar MVC como organización principal.
- Usar RecyclerView para listas.
- Asociar todo dato financiero al `firebase_uid`.
- Mantener el diseño visual en `DESIGN.md`.
- Evitar lógica excesiva en Activities.
- Validar antes de guardar.
- Manejar errores de forma amigable.
- Documentar decisiones importantes.

---

## 20. Conclusión

Este proyecto integrará varias competencias importantes de desarrollo móvil Android: autenticación, persistencia local, consumo de servicios externos, arquitectura de software, manejo de listas, validaciones, navegación y diseño de interfaces intuitivas.

La combinación propuesta permite cumplir los requerimientos académicos y técnicos del proyecto:

- Firebase Authentication para acceso seguro.
- SQLite para persistencia local.
- Retrofit para API REST.
- MVC para organización del código.
- XML para interfaces compatibles con las guías.
- DESIGN.md para lineamientos visuales.
- SKILL.md como guía técnica de desarrollo.

El resultado esperado es una aplicación móvil clara, funcional y útil para estudiantes universitarios que necesitan controlar sus finanzas personales de manera sencilla.
