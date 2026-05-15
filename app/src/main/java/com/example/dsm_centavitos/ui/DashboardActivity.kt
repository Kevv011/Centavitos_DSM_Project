package com.example.dsm_centavitos.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dsm_centavitos.adapter.MovimientoAdapter
import com.example.dsm_centavitos.controller.DashboardController
import com.example.dsm_centavitos.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.NumberFormat
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var dashboardController: DashboardController
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dashboardController = DashboardController(this)

        setupUI()
        loadData()
    }

    private fun setupUI() {
        binding.rvUltimosMovimientos.layoutManager = LinearLayoutManager(this)
        
        binding.fabAddMovimiento.setOnClickListener {
            startActivity(Intent(this, MovimientoFormActivity::class.java))
        }

        binding.btnNavPerfil.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        binding.btnNavHistorial.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
        }

        binding.btnNavPresupuestos.setOnClickListener {
            startActivity(Intent(this, PresupuestoActivity::class.java))
        }
        
        binding.btnNavReportes.setOnClickListener {
            startActivity(Intent(this, ReportesActivity::class.java))
        }
    }

    private fun loadData() {
        val uid = auth.currentUser?.uid ?: return
        
        // Cargar últimos 5 movimientos usando el nuevo controlador
        val movimientos = dashboardController.getLastMovements(uid)
        binding.rvUltimosMovimientos.adapter = MovimientoAdapter(movimientos, 
            onItemClick = { mov -> 
                // Acción opcional al clic
            }, 
            onItemLongClick = { mov ->
                // Acción opcional al long clic
            }
        )

        // Obtener resumen del mes actual usando el nuevo controlador
        val (saldo, ingresos, gastos) = dashboardController.getCurrentMonthSummary(uid)

        val formatter = NumberFormat.getCurrencyInstance(Locale("en", "US"))
        binding.tvSaldoTotal.text = formatter.format(saldo)
        binding.tvIngresosMes.text = "+${formatter.format(ingresos)}"
        binding.tvGastosMes.text = "-${formatter.format(gastos)}"

        // Cargar alertas de presupuesto
        val alerts = dashboardController.getBudgetAlerts(uid)
        if (alerts.isNotEmpty()) {
            binding.cardAlerta.visibility = View.VISIBLE
            binding.tvAlertaMensaje.text = alerts.joinToString("\n")
        } else {
            binding.cardAlerta.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
}
