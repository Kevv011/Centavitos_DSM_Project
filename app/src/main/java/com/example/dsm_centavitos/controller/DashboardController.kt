package com.example.dsm_centavitos.controller

import android.content.Context
import com.example.dsm_centavitos.model.MovimientoExtended
import java.util.Calendar

class DashboardController(private val context: Context) {
    private val movimientoController = MovimientoController(context)

    /**
     * Obtiene el resumen financiero total del usuario.
     * @return Triple con (Saldo, Ingresos, Gastos)
     */
    fun getTotalSummary(uid: String): Triple<Double, Double, Double> {
        val movimientos = movimientoController.getAllMovimientosExtended(uid)
        val ingresos = movimientos.filter { it.tipo.equals("INGRESO", ignoreCase = true) }.sumOf { it.monto }
        val gastos = movimientos.filter { it.tipo.equals("GASTO", ignoreCase = true) }.sumOf { it.monto }
        val saldo = ingresos - gastos
        return Triple(saldo, ingresos, gastos)
    }

    /**
     * Obtiene el resumen financiero del mes actual.
     * @return Triple con (Saldo del mes, Ingresos del mes, Gastos del mes)
     */
    fun getCurrentMonthSummary(uid: String): Triple<Double, Double, Double> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1 
        val currentYear = calendar.get(Calendar.YEAR)

        val movimientos = movimientoController.getAllMovimientosExtended(uid).filter { mov ->
            val dateParts = mov.fecha.split("-")
            if (dateParts.size >= 2) {
                val year = dateParts[0].toIntOrNull() ?: 0
                val month = dateParts[1].toIntOrNull() ?: 0
                year == currentYear && month == currentMonth
            } else {
                false
            }
        }

        val ingresos = movimientos.filter { it.tipo.equals("INGRESO", ignoreCase = true) }.sumOf { it.monto }
        val gastos = movimientos.filter { it.tipo.equals("GASTO", ignoreCase = true) }.sumOf { it.monto }
        val saldo = ingresos - gastos
        
        return Triple(saldo, ingresos, gastos)
    }

    /**
     * Obtiene alertas críticas basadas en el consumo de los presupuestos del mes actual.
     * @return Lista de strings con mensajes de alerta.
     */
    fun getBudgetAlerts(uid: String): List<String> {
        val alerts = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        val mes = calendar.get(Calendar.MONTH) + 1
        val anio = calendar.get(Calendar.YEAR)

        val presupuestos = PresupuestoController(context).getPresupuestos(uid, mes, anio)
        val movimientosMes = movimientoController.getAllMovimientosExtended(uid).filter { mov ->
            val dateParts = mov.fecha.split("-")
            if (dateParts.size >= 2) {
                val m = dateParts[1].toIntOrNull() ?: 0
                val a = dateParts[0].toIntOrNull() ?: 0
                m == mes && a == anio
            } else false
        }

        val categoriaController = CategoriaController(context)
        val categorias = categoriaController.getCategorias(null, uid)

        for (pre in presupuestos) {
            val gastoCategoria = movimientosMes.filter { it.categoriaId == pre.categoriaId }.sumOf { it.monto }
            val porcentaje = if (pre.montoLimite > 0) (gastoCategoria / pre.montoLimite * 100).toInt() else 0
            
            val catNombre = categorias.find { it.id == pre.categoriaId }?.nombre ?: "una categoría"

            if (porcentaje >= 100) {
                alerts.add("¡Cuidado! Has excedido el presupuesto en $catNombre.")
            } else if (porcentaje >= 80) {
                alerts.add("Atención: Has consumido el $porcentaje% de tu presupuesto en $catNombre.")
            }
        }

        return alerts
    }

    /**
     * Obtiene los últimos N movimientos para mostrar en el Dashboard.
     */
    fun getLastMovements(uid: String, count: Int = 5): List<MovimientoExtended> {
        return movimientoController.getAllMovimientosExtended(uid).take(count)
    }
}
