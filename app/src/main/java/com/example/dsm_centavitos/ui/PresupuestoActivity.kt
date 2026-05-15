package com.example.dsm_centavitos.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.adapter.PresupuestoAdapter
import com.example.dsm_centavitos.controller.CategoriaController
import com.example.dsm_centavitos.controller.PresupuestoController
import com.example.dsm_centavitos.databinding.ActivityPresupuestoBinding
import com.example.dsm_centavitos.model.Alerta
import com.example.dsm_centavitos.model.Categoria
import com.example.dsm_centavitos.model.Presupuesto
import com.example.dsm_centavitos.model.PresupuestoExtended
import com.google.firebase.auth.FirebaseAuth
import androidx.recyclerview.widget.LinearLayoutManager
import java.text.SimpleDateFormat
import java.util.*

class PresupuestoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPresupuestoBinding
    private val auth = FirebaseAuth.getInstance()
    private lateinit var categoriaController: CategoriaController
    private lateinit var presupuestoController: PresupuestoController
    
    private var categorias = listOf<Categoria>()
    private val meses = listOf("Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPresupuestoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoriaController = CategoriaController(this)
        presupuestoController = PresupuestoController(this)

        setupSpinners()
        
        val calendar = Calendar.getInstance()
        binding.etYear.setText(calendar.get(Calendar.YEAR).toString())
        binding.spMonth.setSelection(calendar.get(Calendar.MONTH))

        binding.btnSave.setOnClickListener {
            savePresupuesto()
        }

        loadBudgets()
    }

    private fun setupSpinners() {
        val uid = auth.currentUser?.uid
        categorias = categoriaController.getCategorias("GASTO", uid)
        
        val catNames = categorias.map { it.nombre }
        val catAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, catNames)
        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCategory.adapter = catAdapter

        val monthAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, meses)
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spMonth.adapter = monthAdapter
    }

    private fun savePresupuesto() {
        val uid = auth.currentUser?.uid ?: return
        val montoStr = binding.etAmount.text.toString()
        val anioStr = binding.etYear.text.toString()

        if (montoStr.isEmpty() || anioStr.isEmpty() || binding.spCategory.selectedItemPosition == -1) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        val monto = montoStr.toDouble()
        val anio = anioStr.toInt()
        val mes = binding.spMonth.selectedItemPosition + 1
        val categoriaId = categorias[binding.spCategory.selectedItemPosition].id

        val presupuesto = Presupuesto(
            firebaseUid = uid,
            categoriaId = categoriaId,
            montoLimite = monto,
            mes = mes,
            anio = anio
        )

        val id = presupuestoController.insertPresupuesto(presupuesto)
        if (id > 0) {
            Toast.makeText(this, "Presupuesto guardado", Toast.LENGTH_SHORT).show()
            checkAlerts(uid, categoriaId, monto, mes, anio, id.toInt())
            binding.etAmount.text?.clear()
            loadBudgets()
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkAlerts(uid: String, catId: Int, limite: Double, mes: Int, anio: Int, preId: Int) {
        val gastoActual = presupuestoController.getGastoAcumulado(uid, catId, mes, anio)
        val porcentaje = (gastoActual / limite) * 100
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val fecha = sdf.format(Date())

        if (porcentaje >= 100) {
            presupuestoController.insertAlerta(Alerta(
                firebaseUid = uid,
                presupuestoId = preId,
                tipo = "EXCEDIDO",
                mensaje = "Has superado el límite de presupuesto para esta categoría.",
                fecha = fecha,
                estado = "ACTIVA"
            ))
        } else if (porcentaje >= 80) {
            presupuestoController.insertAlerta(Alerta(
                firebaseUid = uid,
                presupuestoId = preId,
                tipo = "ADVERTENCIA",
                mensaje = "Has alcanzado el 80% de tu presupuesto.",
                fecha = fecha,
                estado = "ACTIVA"
            ))
        }
    }

    private fun loadBudgets() {
        val uid = auth.currentUser?.uid ?: return
        val calendar = Calendar.getInstance()
        val mes = calendar.get(Calendar.MONTH) + 1
        val anio = calendar.get(Calendar.YEAR)
        
        val budgets = presupuestoController.getPresupuestosExtended(uid, mes, anio)
        
        binding.rvBudgets.layoutManager = LinearLayoutManager(this)
        binding.rvBudgets.adapter = PresupuestoAdapter(budgets)
    }
}
