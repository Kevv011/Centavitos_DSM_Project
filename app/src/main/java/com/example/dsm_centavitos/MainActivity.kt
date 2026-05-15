package com.example.dsm_centavitos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.adapter.AlertaAdapter
import com.example.dsm_centavitos.controller.DashboardController
import com.example.dsm_centavitos.databinding.ActivityMainBinding
import com.example.dsm_centavitos.ui.HistorialActivity
import com.example.dsm_centavitos.ui.MovimientoFormActivity
import com.example.dsm_centavitos.ui.PerfilActivity
import com.example.dsm_centavitos.ui.PresupuestoActivity
import com.example.dsm_centavitos.ui.ReportesActivity
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()
    private lateinit var dashboardController: DashboardController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dashboardController = DashboardController(this)

        setupNavigation()
        loadDashboardData()
    }

    override fun onResume() {
        super.onResume()
        loadDashboardData()
    }

    private fun setupNavigation() {
        binding.btnGoToProfile.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        binding.btnAddMovement.setOnClickListener {
            startActivity(Intent(this, MovimientoFormActivity::class.java))
        }

        binding.btnViewHistory.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
        }

        binding.btnBudgets.setOnClickListener {
            startActivity(Intent(this, PresupuestoActivity::class.java))
        }

        binding.btnReports.setOnClickListener {
            startActivity(Intent(this, ReportesActivity::class.java))
        }

        binding.btnNews.setOnClickListener {
            startActivity(Intent(this, com.example.dsm_centavitos.ui.NoticiasActivity::class.java))
        }
    }

    private fun loadDashboardData() {
        val user = auth.currentUser
        if (user != null) {
            binding.tvUserName.text = user.email?.split("@")?.get(0) ?: "Usuario"
            
            val calendar = Calendar.getInstance()
            val mes = calendar.get(Calendar.MONTH) + 1
            val anio = calendar.get(Calendar.YEAR)

            val ingresos = dashboardController.getTotalIngresos(user.uid, mes, anio)
            val gastos = dashboardController.getTotalGastos(user.uid, mes, anio)
            val saldo = ingresos - gastos

            binding.tvTotalIncome.text = "$ %.2f".format(ingresos)
            binding.tvTotalExpense.text = "$ %.2f".format(gastos)
            binding.tvTotalBalance.text = "$ %.2f".format(saldo)

            val alertas = dashboardController.getAlertasActivas(user.uid)
            binding.rvAlerts.layoutManager = LinearLayoutManager(this)
            binding.rvAlerts.adapter = AlertaAdapter(alertas)
        }
    }
}
