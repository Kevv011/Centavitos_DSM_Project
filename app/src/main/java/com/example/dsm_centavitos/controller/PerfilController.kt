package com.example.dsm_centavitos.controller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Usuario

class PerfilController(context: Context) {
    private val dbHelper = HelperDB(context)

    fun saveOrUpdatePerfil(usuario: Usuario): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_USER_UID, usuario.firebaseUid)
            put(HelperDB.COLUMN_USER_NOMBRE, usuario.nombre)
            put(HelperDB.COLUMN_USER_APELLIDO, usuario.apellido)
            put(HelperDB.COLUMN_USER_CORREO, usuario.correo)
            put(HelperDB.COLUMN_USER_CARRERA, usuario.carrera)
            put(HelperDB.COLUMN_USER_MONEDA, usuario.moneda)
        }

        val result = db.insertWithOnConflict(
            HelperDB.TABLE_USUARIOS,
            null,
            values,
            SQLiteDatabase.CONFLICT_REPLACE
        )
        return result != -1L
    }

    fun getPerfil(uid: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            HelperDB.TABLE_USUARIOS,
            null,
            "${HelperDB.COLUMN_USER_UID} = ?",
            arrayOf(uid),
            null, null, null
        )

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_UID)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_NOMBRE)),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_APELLIDO)),
                correo = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_CORREO)),
                carrera = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_CARRERA)),
                moneda = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_USER_MONEDA))
            )
        }
        cursor.close()
        return usuario
    }
}
