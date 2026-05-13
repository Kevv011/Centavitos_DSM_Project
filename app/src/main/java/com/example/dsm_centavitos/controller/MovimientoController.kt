package com.example.dsm_centavitos.controller

import android.content.ContentValues
import android.content.Context
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Movimiento

class MovimientoController(context: Context) {
    private val dbHelper = HelperDB(context)

    fun insertMovimiento(movimiento: Movimiento): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_MOV_UID, movimiento.firebaseUid)
            put(HelperDB.COLUMN_MOV_TIPO, movimiento.tipo)
            put(HelperDB.COLUMN_MOV_MONTO, movimiento.monto)
            put(HelperDB.COLUMN_MOV_CAT_ID, movimiento.categoriaId)
            put(HelperDB.COLUMN_MOV_FECHA, movimiento.fecha)
            put(HelperDB.COLUMN_MOV_METODO, movimiento.metodoPago)
            put(HelperDB.COLUMN_MOV_DESC, movimiento.descripcion)
        }
        return db.insert(HelperDB.TABLE_MOVIMIENTOS, null, values)
    }

    fun getAllMovimientos(uid: String): List<Movimiento> {
        val db = dbHelper.readableDatabase
        val movimientos = mutableListOf<Movimiento>()
        
        val cursor = db.query(
            HelperDB.TABLE_MOVIMIENTOS,
            null,
            "${HelperDB.COLUMN_MOV_UID} = ?",
            arrayOf(uid),
            null, null, "${HelperDB.COLUMN_MOV_FECHA} DESC"
        )

        if (cursor.moveToFirst()) {
            do {
                movimientos.add(
                    Movimiento(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_ID)),
                        firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_UID)),
                        tipo = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_TIPO)),
                        monto = cursor.getDouble(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_MONTO)),
                        categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_CAT_ID)),
                        fecha = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_FECHA)),
                        metodoPago = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_METODO)),
                        descripcion = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_DESC))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return movimientos
    }

    fun deleteMovimiento(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(HelperDB.TABLE_MOVIMIENTOS, "${HelperDB.COLUMN_MOV_ID} = ?", arrayOf(id.toString()))
    }
}
