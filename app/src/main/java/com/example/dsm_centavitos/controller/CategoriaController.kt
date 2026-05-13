package com.example.dsm_centavitos.controller

import android.content.ContentValues
import android.content.Context
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Categoria

class CategoriaController(context: Context) {
    private val dbHelper = HelperDB(context)

    fun getCategorias(tipo: String?, uid: String?): List<Categoria> {
        val db = dbHelper.readableDatabase
        val categorias = mutableListOf<Categoria>()
        
        val selection = StringBuilder()
        val selectionArgs = mutableListOf<String>()

        // Siempre traer categorías globales (uid is null) o del usuario específico
        selection.append("(${HelperDB.COLUMN_CAT_UID} IS NULL")
        if (uid != null) {
            selection.append(" OR ${HelperDB.COLUMN_CAT_UID} = ?")
            selectionArgs.add(uid)
        }
        selection.append(")")

        if (tipo != null) {
            selection.append(" AND ${HelperDB.COLUMN_CAT_TIPO} = ?")
            selectionArgs.add(tipo)
        }

        val cursor = db.query(
            HelperDB.TABLE_CATEGORIAS,
            null,
            selection.toString(),
            selectionArgs.toTypedArray(),
            null, null, "${HelperDB.COLUMN_CAT_NOMBRE} ASC"
        )

        if (cursor.moveToFirst()) {
            do {
                categorias.add(
                    Categoria(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_ID)),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_NOMBRE)),
                        tipo = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_TIPO)),
                        icono = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_ICONO)),
                        color = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_COLOR)),
                        firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_UID))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return categorias
    }

    fun insertCategoria(categoria: Categoria): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_CAT_NOMBRE, categoria.nombre)
            put(HelperDB.COLUMN_CAT_TIPO, categoria.tipo)
            put(HelperDB.COLUMN_CAT_ICONO, categoria.icono)
            put(HelperDB.COLUMN_CAT_COLOR, categoria.color)
            put(HelperDB.COLUMN_CAT_UID, categoria.firebaseUid)
        }
        return db.insert(HelperDB.TABLE_CATEGORIAS, null, values)
    }
}
