package com.example.dsm_centavitos.controller

import android.content.Context
import com.example.dsm_centavitos.db.HelperDB
import com.example.dsm_centavitos.model.Alerta
import com.example.dsm_centavitos.model.ReporteCategoria

class DashboardController(context: Context) {
    private val dbHelper = HelperDB(context)

    fun getTotalIngresos(uid: String, mes: Int, anio: Int): Double {
        val db = dbHelper.readableDatabase
        val mesStr = if (mes < 10) "0$mes" else mes.toString()
        val pattern = "$anio-$mesStr-%"
        
        val cursor = db.rawQuery(
            "SELECT SUM(${HelperDB.COLUMN_MOV_MONTO}) FROM ${HelperDB.TABLE_MOVIMIENTOS} " +
                    "WHERE ${HelperDB.COLUMN_MOV_UID} = ? " +
                    "AND ${HelperDB.COLUMN_MOV_FECHA} LIKE ? " +
                    "AND ${HelperDB.COLUMN_MOV_TIPO} = 'INGRESO'",
            arrayOf(uid, pattern)
        )
        
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }

    fun getTotalGastos(uid: String, mes: Int, anio: Int): Double {
        val db = dbHelper.readableDatabase
        val mesStr = if (mes < 10) "0$mes" else mes.toString()
        val pattern = "$anio-$mesStr-%"
        
        val cursor = db.rawQuery(
            "SELECT SUM(${HelperDB.COLUMN_MOV_MONTO}) FROM ${HelperDB.TABLE_MOVIMIENTOS} " +
                    "WHERE ${HelperDB.COLUMN_MOV_UID} = ? " +
                    "AND ${HelperDB.COLUMN_MOV_FECHA} LIKE ? " +
                    "AND ${HelperDB.COLUMN_MOV_TIPO} = 'GASTO'",
            arrayOf(uid, pattern)
        )
        
        var total = 0.0
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0)
        }
        cursor.close()
        return total
    }

    fun getAlertasActivas(uid: String): List<Alerta> {
        val db = dbHelper.readableDatabase
        val alertas = mutableListOf<Alerta>()
        val cursor = db.query(
            HelperDB.TABLE_ALERTAS,
            null,
            "${HelperDB.COLUMN_ALE_UID} = ? AND ${HelperDB.COLUMN_ALE_ESTADO} = 'ACTIVA'",
            arrayOf(uid),
            null, null, "${HelperDB.COLUMN_ALE_FECHA} DESC"
        )
        
        if (cursor.moveToFirst()) {
            do {
                alertas.add(Alerta(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_ID)),
                    firebaseUid = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_UID)),
                    presupuestoId = cursor.getInt(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_PRE_ID)),
                    tipo = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_TIPO)),
                    mensaje = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_MSG)),
                    fecha = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_FECHA)),
                    estado = cursor.getString(cursor.getColumnIndexOrThrow(HelperDB.COLUMN_ALE_ESTADO))
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return alertas
    }

    fun getGastosPorCategoria(uid: String, mes: Int, anio: Int): List<ReporteCategoria> {
        val db = dbHelper.readableDatabase
        val list = mutableListOf<ReporteCategoria>()
        val mesStr = if (mes < 10) "0$mes" else mes.toString()
        val pattern = "$anio-$mesStr-%"

        val query = """
            SELECT c.${HelperDB.COLUMN_CAT_NOMBRE}, SUM(m.${HelperDB.COLUMN_MOV_MONTO}) as total
            FROM ${HelperDB.TABLE_MOVIMIENTOS} m
            JOIN ${HelperDB.TABLE_CATEGORIAS} c ON m.${HelperDB.COLUMN_MOV_CAT_ID} = c.${HelperDB.COLUMN_CAT_ID}
            WHERE m.${HelperDB.COLUMN_MOV_UID} = ? 
            AND m.${HelperDB.COLUMN_MOV_FECHA} LIKE ? 
            AND m.${HelperDB.COLUMN_MOV_TIPO} = 'GASTO'
            GROUP BY c.${HelperDB.COLUMN_CAT_ID}
            ORDER BY total DESC
        """

        val cursor = db.rawQuery(query, arrayOf(uid, pattern))
        if (cursor.moveToFirst()) {
            do {
                list.add(ReporteCategoria(
                    nombre = cursor.getString(0),
                    monto = cursor.getDouble(1)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }
}
