package com.example.dsm_centavitos.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HelperDB(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "centavitos.db"
        private const val DATABASE_VERSION = 1

        // Tabla Usuarios Locales
        const val TABLE_USUARIOS = "usuarios_locales"
        const val COLUMN_ID = "id"
        const val COLUMN_UID = "firebase_uid"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_CORREO = "correo"
        const val COLUMN_CARRERA = "carrera"
        const val COLUMN_MONEDA = "moneda"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createUsuariosTable = ("CREATE TABLE $TABLE_USUARIOS (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_UID TEXT NOT NULL UNIQUE, " +
                "$COLUMN_NOMBRE TEXT, " +
                "$COLUMN_APELLIDO TEXT, " +
                "$COLUMN_CORREO TEXT NOT NULL, " +
                "$COLUMN_CARRERA TEXT, " +
                "$COLUMN_MONEDA TEXT)")
        db?.execSQL(createUsuariosTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }
}
