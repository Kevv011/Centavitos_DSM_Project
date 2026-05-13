package com.example.dsm_centavitos.controller

import android.content.ContentValues
import android.content.Context
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Movimiento
import com.example.dsm_centavitos.model.MovimientoExtended

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

    fun getAllMovimientosExtended(uid: String): List<MovimientoExtended> {
        val db = dbHelper.readableDatabase
        val movimientos = mutableListOf<MovimientoExtended>()
        
        val query = """
            SELECT m.*, c.${HelperDB.COLUMN_CAT_NOMBRE}, c.${HelperDB.COLUMN_CAT_COLOR}, c.${HelperDB.COLUMN_CAT_ICONO}
            FROM ${HelperDB.TABLE_MOVIMIENTOS} m
            JOIN ${HelperDB.TABLE_CATEGORIAS} c ON m.${HelperDB.COLUMN_MOV_CAT_ID} = c.${HelperDB.COLUMN_CAT_ID}
            WHERE m.${HelperDB.COLUMN_MOV_UID} = ?
            ORDER BY m.${HelperDB.COLUMN_MOV_FECHA} DESC, m.${HelperDB.COLUMN_MOV_ID} DESC
        """

        val cursor = db.rawQuery(query, arrayOf(uid))

        if (cursor.moveToFirst()) {
            do {
                movimientos.add(
                    MovimientoExtended(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_ID)),
                        firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_UID)),
                        tipo = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_TIPO)),
                        monto = cursor.getDouble(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_MONTO)),
                        categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_CAT_ID)),
                        fecha = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_FECHA)),
                        metodoPago = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_METODO)),
                        descripcion = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_DESC)),
                        categoriaNombre = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_NOMBRE)),
                        categoriaColor = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_COLOR)),
                        categoriaIcono = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_ICONO))
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

    fun getMovimientoById(id: Int): Movimiento? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            HelperDB.TABLE_MOVIMIENTOS,
            null,
            "${HelperDB.COLUMN_MOV_ID} = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        var mov: Movimiento? = null
        if (cursor.moveToFirst()) {
            mov = Movimiento(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_ID)),
                firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_UID)),
                tipo = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_TIPO)),
                monto = cursor.getDouble(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_MONTO)),
                categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_CAT_ID)),
                fecha = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_FECHA)),
                metodoPago = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_METODO)),
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_MOV_DESC))
            )
        }
        cursor.close()
        return mov
    }

    fun updateMovimiento(movimiento: Movimiento): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_MOV_TIPO, movimiento.tipo)
            put(HelperDB.COLUMN_MOV_MONTO, movimiento.monto)
            put(HelperDB.COLUMN_MOV_CAT_ID, movimiento.categoriaId)
            put(HelperDB.COLUMN_MOV_FECHA, movimiento.fecha)
            put(HelperDB.COLUMN_MOV_METODO, movimiento.metodoPago)
            put(HelperDB.COLUMN_MOV_DESC, movimiento.descripcion)
        }
        return db.update(HelperDB.TABLE_MOVIMIENTOS, values, "${HelperDB.COLUMN_MOV_ID} = ?", arrayOf(movimiento.id.toString()))
    }
}
