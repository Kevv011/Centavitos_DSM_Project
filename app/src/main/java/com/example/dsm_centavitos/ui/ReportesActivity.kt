package com.example.dsm_centavitos.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dsm_centavitos.adapter.CategoriaReporte
import com.example.dsm_centavitos.adapter.ReporteCategoriaAdapter
import com.example.dsm_centavitos.controller.CategoriaController
import com.example.dsm_centavitos.controller.MovimientoController
import com.example.dsm_centavitos.databinding.ActivityReportesBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.NumberFormat
import java.util.*

class ReportesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportesBinding
    private lateinit var movimientoController: MovimientoController
    private lateinit var categoriaController: CategoriaController
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movimientoController = MovimientoController(this)
        categoriaController = CategoriaController(this)

        setupUI()
        loadReport()
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.rvReporteCategorias.layoutManager = LinearLayoutManager(this)
    }

    private fun loadReport() {
        val uid = auth.currentUser?.uid ?: return
        val calendar = Calendar.getInstance()
        val mes = calendar.get(Calendar.MONTH) + 1
        val anio = calendar.get(Calendar.YEAR)
        
        // Formatear mes y año para el título
        val mesNombre = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale("es", "ES")) ?: ""
        binding.tvMesReporte.text = String.format("%s %d", mesNombre.replaceFirstChar { it.uppercase() }, anio)

        // Obtener todos los movimientos de gasto del mes
        val movimientos = movimientoController.getAllMovimientosExtended(uid).filter { mov ->
            val dateParts = mov.fecha.split("-")
            if (dateParts.size >= 2) {
                val m = dateParts[1].toIntOrNull() ?: 0
                val a = dateParts[0].toIntOrNull() ?: 0
                m == mes && a == anio && mov.tipo.equals("GASTO", ignoreCase = true)
            } else false
        }

        if (movimientos.isEmpty()) {
            binding.tvEmptyReporte.visibility = View.VISIBLE
            binding.rvReporteCategorias.visibility = View.GONE
            binding.tvTotalGastosReporte.text = formatCurrency(0.0)
            return
        }

        binding.tvEmptyReporte.visibility = View.GONE
        binding.rvReporteCategorias.visibility = View.VISIBLE

        val totalGastos = movimientos.sumOf { it.monto }
        binding.tvTotalGastosReporte.text = formatCurrency(totalGastos)

        // Agrupar por categoría
        val categorias = categoriaController.getCategorias(null, uid)
        val reporteItems = movimientos.groupBy { it.categoriaId }
            .map { (catId, movs) ->
                val cat = categorias.find { it.id == catId }
                val montoCat = movs.sumOf { it.monto }
                val porcentaje = if (totalGastos > 0) (montoCat / totalGastos * 100).toInt() else 0
                CategoriaReporte(
                    nombre = cat?.nombre ?: "Sin categoría",
                    monto = montoCat,
                    porcentaje = porcentaje,
                    color = cat?.color
                )
            }.sortedByDescending { it.monto }

        binding.rvReporteCategorias.adapter = ReporteCategoriaAdapter(reporteItems)
    }

    private fun formatCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount)
    }
}
