package com.example.dsm_centavitos.controller

import android.content.ContentValues
import android.content.Context
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Presupuesto
import com.example.dsm_centavitos.model.Alerta
import com.example.dsm_centavitos.model.PresupuestoExtended

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

    fun getGastoAcumulado(uid: String, categoriaId: Int, mes: Int, anio: Int): Double {
        val db = dbHelper.readableDatabase
        // Formato de fecha esperado: "YYYY-MM-DD"
        // Buscamos movimientos del mes y año específicos para esa categoría
        val mesStr = if (mes < 10) "0$mes" else mes.toString()
        val pattern = "$anio-$mesStr-%"
        
        val cursor = db.rawQuery(
            "SELECT SUM(${HelperDB.COLUMN_MOV_MONTO}) FROM ${HelperDB.TABLE_MOVIMIENTOS} " +
                    "WHERE ${HelperDB.COLUMN_MOV_UID} = ? " +
                    "AND ${HelperDB.COLUMN_MOV_CAT_ID} = ? " +
                    "AND ${HelperDB.COLUMN_MOV_FECHA} LIKE ? " +
                    "AND ${HelperDB.COLUMN_MOV_TIPO} = 'GASTO'",
            arrayOf(uid, categoriaId.toString(), pattern)
        )
        
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }

    fun deletePresupuesto(id: Int): Int {
        val db = dbHelper.writableDatabase
        return db.delete(HelperDB.TABLE_PRESUPUESTOS, "${HelperDB.COLUMN_PRE_ID} = ?", arrayOf(id.toString()))
    }

    fun getPresupuestosExtended(uid: String, mes: Int, anio: Int): List<PresupuestoExtended> {
        val db = dbHelper.readableDatabase
        val list = mutableListOf<PresupuestoExtended>()
        
        val query = """
            SELECT p.*, c.${HelperDB.COLUMN_CAT_NOMBRE}
            FROM ${HelperDB.TABLE_PRESUPUESTOS} p
            JOIN ${HelperDB.TABLE_CATEGORIAS} c ON p.${HelperDB.COLUMN_PRE_CAT_ID} = c.${HelperDB.COLUMN_CAT_ID}
            WHERE p.${HelperDB.COLUMN_PRE_UID} = ? AND p.${HelperDB.COLUMN_PRE_MES} = ? AND p.${HelperDB.COLUMN_PRE_ANIO} = ?
        """
        
        val cursor = db.rawQuery(query, arrayOf(uid, mes.toString(), anio.toString()))
        
        if (cursor.moveToFirst()) {
            do {
                val preId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_ID))
                val catId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_CAT_ID))
                val limite = cursor.getDouble(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_PRE_MONTO))
                val catName = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_CAT_NOMBRE))
                
                val gasto = getGastoAcumulado(uid, catId, mes, anio)
                
                list.add(PresupuestoExtended(
                    id = preId,
                    firebaseUid = uid,
                    categoriaId = catId,
                    montoLimite = limite,
                    mes = mes,
                    anio = anio,
                    categoriaNombre = catName,
                    gastoActual = gasto
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
