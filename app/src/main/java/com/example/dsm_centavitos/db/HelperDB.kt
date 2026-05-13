package com.example.dsm_centavitos.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "centavitos.db"
        private const val DATABASE_VERSION = 2

        // Tabla Usuarios Locales
        const val TABLE_USUARIOS = "usuarios_locales"
        const val COLUMN_USER_ID = "id"
        const val COLUMN_USER_UID = "firebase_uid"
        const val COLUMN_USER_NOMBRE = "nombre"
        const val COLUMN_USER_APELLIDO = "apellido"
        const val COLUMN_USER_CORREO = "correo"
        const val COLUMN_USER_CARRERA = "carrera"
        const val COLUMN_USER_MONEDA = "moneda"

        // Tabla Categorias
        const val TABLE_CATEGORIAS = "categorias"
        const val COLUMN_CAT_ID = "id"
        const val COLUMN_CAT_NOMBRE = "nombre"
        const val COLUMN_CAT_TIPO = "tipo"
        const val COLUMN_CAT_ICONO = "icono"
        const val COLUMN_CAT_COLOR = "color"
        const val COLUMN_CAT_UID = "firebase_uid"

        // Tabla Movimientos
        const val TABLE_MOVIMIENTOS = "movimientos"
        const val COLUMN_MOV_ID = "id"
        const val COLUMN_MOV_UID = "firebase_uid"
        const val COLUMN_MOV_TIPO = "tipo"
        const val COLUMN_MOV_MONTO = "monto"
        const val COLUMN_MOV_CAT_ID = "categoria_id"
        const val COLUMN_MOV_FECHA = "fecha"
        const val COLUMN_MOV_METODO = "metodo_pago"
        const val COLUMN_MOV_DESC = "descripcion"

        // Tabla Presupuestos
        const val TABLE_PRESUPUESTOS = "presupuestos"
        const val COLUMN_PRE_ID = "id"
        const val COLUMN_PRE_UID = "firebase_uid"
        const val COLUMN_PRE_CAT_ID = "categoria_id"
        const val COLUMN_PRE_MONTO = "monto_limite"
        const val COLUMN_PRE_MES = "mes"
        const val COLUMN_PRE_ANIO = "anio"

        // Tabla Alertas
        const val TABLE_ALERTAS = "alertas"
        const val COLUMN_ALE_ID = "id"
        const val COLUMN_ALE_UID = "firebase_uid"
        const val COLUMN_ALE_PRE_ID = "presupuesto_id"
        const val COLUMN_ALE_TIPO = "tipo"
        const val COLUMN_ALE_MSG = "mensaje"
        const val COLUMN_ALE_FECHA = "fecha"
        const val COLUMN_ALE_ESTADO = "estado"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Habilitar llaves foráneas
        db?.execSQL("PRAGMA foreign_keys = ON;")

        val createUsuariosTable = ("CREATE TABLE $TABLE_USUARIOS (" +
                "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_USER_UID TEXT NOT NULL UNIQUE, " +
                "$COLUMN_USER_NOMBRE TEXT, " +
                "$COLUMN_USER_APELLIDO TEXT, " +
                "$COLUMN_USER_CORREO TEXT NOT NULL, " +
                "$COLUMN_USER_CARRERA TEXT, " +
                "$COLUMN_USER_MONEDA TEXT)")

        val createCategoriasTable = ("CREATE TABLE $TABLE_CATEGORIAS (" +
                "$COLUMN_CAT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_CAT_NOMBRE TEXT NOT NULL, " +
                "$COLUMN_CAT_TIPO TEXT NOT NULL, " +
                "$COLUMN_CAT_ICONO TEXT, " +
                "$COLUMN_CAT_COLOR TEXT, " +
                "$COLUMN_CAT_UID TEXT)")

        val createMovimientosTable = ("CREATE TABLE $TABLE_MOVIMIENTOS (" +
                "$COLUMN_MOV_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_MOV_UID TEXT NOT NULL, " +
                "$COLUMN_MOV_TIPO TEXT NOT NULL, " +
                "$COLUMN_MOV_MONTO REAL NOT NULL, " +
                "$COLUMN_MOV_CAT_ID INTEGER NOT NULL, " +
                "$COLUMN_MOV_FECHA TEXT NOT NULL, " +
                "$COLUMN_MOV_METODO TEXT, " +
                "$COLUMN_MOV_DESC TEXT, " +
                "FOREIGN KEY($COLUMN_MOV_CAT_ID) REFERENCES $TABLE_CATEGORIAS($COLUMN_CAT_ID))")

        val createPresupuestosTable = ("CREATE TABLE $TABLE_PRESUPUESTOS (" +
                "$COLUMN_PRE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_PRE_UID TEXT NOT NULL, " +
                "$COLUMN_PRE_CAT_ID INTEGER NOT NULL, " +
                "$COLUMN_PRE_MONTO REAL NOT NULL, " +
                "$COLUMN_PRE_MES INTEGER NOT NULL, " +
                "$COLUMN_PRE_ANIO INTEGER NOT NULL, " +
                "FOREIGN KEY($COLUMN_PRE_CAT_ID) REFERENCES $TABLE_CATEGORIAS($COLUMN_CAT_ID))")

        val createAlertasTable = ("CREATE TABLE $TABLE_ALERTAS (" +
                "$COLUMN_ALE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_ALE_UID TEXT NOT NULL, " +
                "$COLUMN_ALE_PRE_ID INTEGER, " +
                "$COLUMN_ALE_TIPO TEXT NOT NULL, " +
                "$COLUMN_ALE_MSG TEXT NOT NULL, " +
                "$COLUMN_ALE_FECHA TEXT NOT NULL, " +
                "$COLUMN_ALE_ESTADO TEXT NOT NULL, " +
                "FOREIGN KEY($COLUMN_ALE_PRE_ID) REFERENCES $TABLE_PRESUPUESTOS($COLUMN_PRE_ID))")

        db?.execSQL(createUsuariosTable)
        db?.execSQL(createCategoriasTable)
        db?.execSQL(createMovimientosTable)
        db?.execSQL(createPresupuestosTable)
        db?.execSQL(createAlertasTable)

        insertDefaultCategories(db)
    }

    private fun insertDefaultCategories(db: SQLiteDatabase?) {
        val categorias = listOf(
            // Gastos
            arrayOf("Alimentación", "GASTO", "restaurant", "#FF5722"),
            arrayOf("Transporte", "GASTO", "directions_bus", "#2196F3"),
            arrayOf("Vivienda", "GASTO", "home", "#795548"),
            arrayOf("Servicios", "GASTO", "receipt", "#607D8B"),
            arrayOf("Educación", "GASTO", "school", "#3F51B5"),
            arrayOf("Ocio", "GASTO", "sports_esports", "#E91E63"),
            arrayOf("Salud", "GASTO", "medical_services", "#F44336"),
            arrayOf("Otros Gastos", "GASTO", "more_horiz", "#9E9E9E"),
            // Ingresos
            arrayOf("Salario", "INGRESO", "payments", "#4CAF50"),
            arrayOf("Beca", "INGRESO", "account_balance", "#009688"),
            arrayOf("Ventas", "INGRESO", "sell", "#FFC107"),
            arrayOf("Otros Ingresos", "INGRESO", "add_circle", "#8BC34A")
        )

        for (cat in categorias) {
            val values = ContentValues().apply {
                put(COLUMN_CAT_NOMBRE, cat[0])
                put(COLUMN_CAT_TIPO, cat[1])
                put(COLUMN_CAT_ICONO, cat[2])
                put(COLUMN_CAT_COLOR, cat[3])
                put(COLUMN_CAT_UID, null as String?) // Globales
            }
            db?.insert(TABLE_CATEGORIAS, null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_ALERTAS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRESUPUESTOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIMIENTOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_CATEGORIAS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        if (!db?.isReadOnly!!) {
            db.execSQL("PRAGMA foreign_keys = ON;")
        }
    }
}
