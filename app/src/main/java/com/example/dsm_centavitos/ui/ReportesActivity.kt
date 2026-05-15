package com.example.dsm_centavitos.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.adapter.ReporteAdapter
import com.example.dsm_centavitos.controller.DashboardController
import com.example.dsm_centavitos.databinding.ActivityReportesBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.*

class ReportesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportesBinding
    private val auth = FirebaseAuth.getInstance()
    private lateinit var dashboardController: DashboardController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dashboardController = DashboardController(this)
        loadReports()
    }

    private fun loadReports() {
        val uid = auth.currentUser?.uid ?: return
        val calendar = Calendar.getInstance()
        val mes = calendar.get(Calendar.MONTH) + 1
        val anio = calendar.get(Calendar.YEAR)

        val reportData = dashboardController.getGastosPorCategoria(uid, mes, anio)
        
        binding.rvReportItems.layoutManager = LinearLayoutManager(this)
        binding.rvReportItems.adapter = ReporteAdapter(reportData)
    }
}
