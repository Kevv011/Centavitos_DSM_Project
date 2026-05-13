package com.example.dsm_centavitos.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.R
import com.example.dsm_centavitos.controller.AuthController
import com.example.dsm_centavitos.controller.CategoriaController
import com.example.dsm_centavitos.controller.MovimientoController
import com.example.dsm_centavitos.databinding.ActivityMovimientoFormBinding
import com.example.dsm_centavitos.model.Categoria
import com.example.dsm_centavitos.model.Movimiento
import java.text.SimpleDateFormat
import java.util.*

class MovimientoFormActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovimientoFormBinding
    private val authController = AuthController()
    private lateinit var categoriaController: CategoriaController
    private lateinit var movimientoController: MovimientoController
    
    private var selectedType = "GASTO"
    private var categoriesList = listOf<Categoria>()
    private var selectedCategoryId: Int = -1
    private val calendar = Calendar.getInstance()
    private var movementId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovimientoFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoriaController = CategoriaController(this)
        movimientoController = MovimientoController(this)

        movementId = intent.getIntExtra("MOVEMENT_ID", -1)

        setupUI()
        loadCategories()
        
        if (movementId != -1) {
            loadMovementData()
        }
    }

    private fun loadMovementData() {
        val mov = movimientoController.getMovimientoById(movementId)
        mov?.let {
            binding.tvFormTitle.text = getString(R.string.edit_movement_title)
            binding.etAmount.setText(it.monto.toString())
            binding.etDescription.setText(it.descripcion)
            
            selectedType = it.tipo
            if (it.tipo == "INGRESO") {
                binding.btnIncome.isChecked = true
            } else {
                binding.btnExpense.isChecked = true
            }

            // Cargar fecha
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            try {
                calendar.time = sdf.parse(it.fecha) ?: Date()
                updateDateLabel()
            } catch (e: Exception) {}

            selectedCategoryId = it.categoriaId
            // Para el AutoCompleteTextView, necesitamos el nombre
            loadCategories() // Recargar para asegurar que el tipo es correcto
        }
    }

    private fun setupUI() {
        // Selector de Tipo
        binding.toggleGroupType.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                selectedType = if (checkedId == binding.btnExpense.id) "GASTO" else "INGRESO"
                loadCategories()
            }
        }

        // Selector de Fecha
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateDateLabel()
        }

        binding.etDate.setOnClickListener {
            DatePickerDialog(
                this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        updateDateLabel()

        // Botón Guardar
        binding.btnSaveMovement.setOnClickListener {
            saveMovement()
        }
    }

    private fun updateDateLabel() {
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        binding.etDate.setText(sdf.format(calendar.time))
    }

    private fun loadCategories() {
        val uid = authController.getUid()
        categoriesList = categoriaController.getCategorias(selectedType, uid)
        
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            categoriesList.map { it.nombre }
        )
        binding.actvCategory.setAdapter(adapter)

        // Si estamos en edición, buscar el nombre de la categoría actual
        if (selectedCategoryId != -1) {
            val cat = categoriesList.find { it.id == selectedCategoryId }
            cat?.let {
                binding.actvCategory.setText(it.nombre, false)
            }
        } else {
            binding.actvCategory.setText("", false)
        }
        
        binding.actvCategory.setOnItemClickListener { _, _, position, _ ->
            selectedCategoryId = categoriesList[position].id
        }
    }

    private fun saveMovement() {
        val uid = authController.getUid() ?: return
        val montoStr = binding.etAmount.text.toString()
        val desc = binding.etDescription.text.toString()
        val fecha = binding.etDate.text.toString()

        if (montoStr.isEmpty() || selectedCategoryId == -1) {
            Toast.makeText(this, "Completa monto y categoría", Toast.LENGTH_SHORT).show()
            return
        }

        val monto = montoStr.toDoubleOrNull() ?: 0.0
        if (monto <= 0) {
            Toast.makeText(this, "El monto debe ser mayor a cero", Toast.LENGTH_SHORT).show()
            return
        }

        val movimiento = Movimiento(
            id = if (movementId != -1) movementId else 0,
            firebaseUid = uid,
            tipo = selectedType,
            monto = monto,
            categoriaId = selectedCategoryId,
            fecha = fecha,
            descripcion = desc
        )

        val result = if (movementId != -1) {
            movimientoController.updateMovimiento(movimiento).toLong()
        } else {
            movimientoController.insertMovimiento(movimiento)
        }

        if (result != -1L) {
            Toast.makeText(this, "Movimiento guardado", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
        }
    }
}
