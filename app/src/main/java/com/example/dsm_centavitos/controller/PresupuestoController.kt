package com.example.dsm_centavitos.controller

import android.content.ContentValues
import android.content.Context
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Presupuesto
import com.example.dsm_centavitos.model.Alerta

class PresupuestoController(context: Context) {
    private val dbHelper = HelperDB(context)

    fun insertPresupuesto(presupuesto: Presupuesto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_PRE_UID, presupuesto.firebaseUid)
            put(HelperDB.COLUMN_PRE_CAT_ID, presupuesto.categoriaId)
            put(HelperDB.COLUMN_PRE_MONTO, presupuesto.montoLimite)
            put(HelperDB.COLUMN_PRE_MES, presupuesto.mes)
            put(HelperDB.COLUMN_PRE_ANIO, presupuesto.anio)
        }
        return db.insert(HelperDB.TABLE_PRESUPUESTOS, null, values)
    }

    fun getPresupuestos(uid: String, mes: Int, anio: Int): List<Presupuesto> {
        val db = dbHelper.readableDatabase
        val presupuestos = mutableListOf<Presupuesto>()
        val cursor = db.query(
            HelperDB.TABLE_PRESUPUESTOS,
            null,
            "${HelperDB.COLUMN_PRE_UID} = ? AND ${HelperDB.COLUMN_PRE_MES} = ? AND ${HelperDB.COLUMN_PRE_ANIO} = ?",
            arrayOf(uid, mes.toString(), anio.toString()),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            do {
                presupuestos.add(
                    Presupuesto(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_ID)),
                        firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_UID)),
                        categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_CAT_ID)),
                        montoLimite = cursor.getDouble(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_MONTO)),
                        mes = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_MES)),
                        anio = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_ANIO))
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return presupuestos
    }

    fun updatePresupuesto(presupuesto: Presupuesto): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_PRE_MONTO, presupuesto.montoLimite)
        }
        return db.update(
            HelperDB.TABLE_PRESUPUESTOS,
            values,
            "${HelperDB.COLUMN_PRE_ID} = ? AND ${HelperDB.COLUMN_PRE_UID} = ?",
            arrayOf(presupuesto.id.toString(), presupuesto.firebaseUid)
        )
    }

    fun deletePresupuesto(id: Int, uid: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete(
            HelperDB.TABLE_PRESUPUESTOS,
            "${HelperDB.COLUMN_PRE_ID} = ? AND ${HelperDB.COLUMN_PRE_UID} = ?",
            arrayOf(id.toString(), uid)
        )
    }

    fun getPresupuestoByCategoria(uid: String, catId: Int, mes: Int, anio: Int): Presupuesto? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            HelperDB.TABLE_PRESUPUESTOS,
            null,
            "${HelperDB.COLUMN_PRE_UID} = ? AND ${HelperDB.COLUMN_PRE_CAT_ID} = ? AND ${HelperDB.COLUMN_PRE_MES} = ? AND ${HelperDB.COLUMN_PRE_ANIO} = ?",
            arrayOf(uid, catId.toString(), mes.toString(), anio.toString()),
            null, null, null
        )
        var pre: Presupuesto? = null
        if (cursor.moveToFirst()) {
            pre = Presupuesto(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_ID)),
                firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_UID)),
                categoriaId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_CAT_ID)),
                montoLimite = cursor.getDouble(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_MONTO)),
                mes = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_MES)),
                anio = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_ANIO))
            )
        }
        cursor.close()
        return pre
    }

    fun insertAlerta(alerta: Alerta): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(HelperDB.COLUMN_ALE_UID, alerta.firebaseUid)
            put(HelperDB.COLUMN_ALE_PRE_ID, alerta.presupuestoId)
            put(HelperDB.COLUMN_ALE_TIPO, alerta.tipo)
            put(HelperDB.COLUMN_ALE_MSG, alerta.mensaje)
            put(HelperDB.COLUMN_ALE_FECHA, alerta.fecha)
            put(HelperDB.COLUMN_ALE_ESTADO, alerta.estado)
        }
        return db.insert(HelperDB.TABLE_ALERTAS, null, values)
    }
}
