package com.example.dsm_centavitos.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dsm_centavitos.R
import com.example.dsm_centavitos.adapter.PresupuestoAdapter
import com.example.dsm_centavitos.controller.CategoriaController
import com.example.dsm_centavitos.controller.MovimientoController
import com.example.dsm_centavitos.controller.PresupuestoController
import com.example.dsm_centavitos.databinding.ActivityPresupuestosBinding
import com.example.dsm_centavitos.databinding.DialogPresupuestoBinding
import com.example.dsm_centavitos.model.Presupuesto
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class PresupuestoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPresupuestosBinding
    private lateinit var presupuestoController: PresupuestoController
    private lateinit var categoriaController: CategoriaController
    private lateinit var movimientoController: MovimientoController
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPresupuestosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presupuestoController = PresupuestoController(this)
        categoriaController = CategoriaController(this)
        movimientoController = MovimientoController(this)

        setupUI()
        loadPresupuestos()
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.rvPresupuestos.layoutManager = LinearLayoutManager(this)

        binding.fabAddPresupuesto.setOnClickListener {
            showPresupuestoDialog(null)
        }
    }

    private fun loadPresupuestos() {
        val uid = auth.currentUser?.uid ?: return
        val calendar = Calendar.getInstance()
        val mes = calendar.get(Calendar.MONTH) + 1
        val anio = calendar.get(Calendar.YEAR)

        val presupuestos = presupuestoController.getPresupuestos(uid, mes, anio)
        
        if (presupuestos.isEmpty()) {
            binding.tvEmptyPresupuestos.visibility = View.VISIBLE
            binding.rvPresupuestos.visibility = View.GONE
        } else {
            binding.tvEmptyPresupuestos.visibility = View.GONE
            binding.rvPresupuestos.visibility = View.VISIBLE
            
            binding.rvPresupuestos.adapter = PresupuestoAdapter(
                presupuestos,
                this,
                onItemClick = { pre -> showPresupuestoDialog(pre) },
                onItemLongClick = { pre -> showDeleteDialog(pre) }
            )
        }
    }

    private fun showPresupuestoDialog(presupuesto: Presupuesto?) {
        val uid = auth.currentUser?.uid ?: ""
        val dialogBinding = DialogPresupuestoBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogBinding.root)
        val dialog = builder.create()

        val categorias = categoriaController.getCategorias("GASTO", uid)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias.map { it.nombre })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dialogBinding.spinnerCategorias.adapter = adapter

        if (presupuesto != null) {
            dialogBinding.tvDialogTitle.text = getString(R.string.edit_presupuesto)
            dialogBinding.etMontoPresupuesto.setText(presupuesto.montoLimite.toString())
            val catIndex = categorias.indexOfFirst { it.id == presupuesto.categoriaId }
            if (catIndex != -1) dialogBinding.spinnerCategorias.setSelection(catIndex)
            dialogBinding.spinnerCategorias.isEnabled = false 
        }

        dialogBinding.btnSavePresupuesto.setOnClickListener {
            val montoStr = dialogBinding.etMontoPresupuesto.text.toString()
            val monto = montoStr.toDoubleOrNull() ?: 0.0

            if (monto <= 0) {
                Toast.makeText(this, R.string.error_monto_invalid, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val calendar = Calendar.getInstance()
            val mes = calendar.get(Calendar.MONTH) + 1
            val anio = calendar.get(Calendar.YEAR)
            val selectedCat = categorias[dialogBinding.spinnerCategorias.selectedItemPosition]

            if (presupuesto == null) {
                val existente = presupuestoController.getPresupuestoByCategoria(uid, selectedCat.id!!, mes, anio)
                if (existente != null) {
                    Toast.makeText(this, "Ya existe un presupuesto para esta categoría", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                val newPre = Presupuesto(
                    firebaseUid = uid,
                    categoriaId = selectedCat.id!!,
                    montoLimite = monto,
                    mes = mes,
                    anio = anio
                )
                presupuestoController.insertPresupuesto(newPre)
            } else {
                val updatedPre = presupuesto.copy(montoLimite = monto)
                presupuestoController.updatePresupuesto(updatedPre)
            }

            Toast.makeText(this, R.string.presupuesto_saved, Toast.LENGTH_SHORT).show()
            loadPresupuestos()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteDialog(presupuesto: Presupuesto) {
        val uid = auth.currentUser?.uid ?: ""
        AlertDialog.Builder(this)
            .setTitle("Eliminar Presupuesto")
            .setMessage("¿Estás seguro de que deseas eliminar este presupuesto?")
            .setPositiveButton("Eliminar") { _, _ ->
                presupuestoController.deletePresupuesto(presupuesto.id!!, uid)
                loadPresupuestos()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
