package com.example.dsm_centavitos.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dsm_centavitos.R
import com.example.dsm_centavitos.adapter.MovimientoAdapter
import com.example.dsm_centavitos.controller.AuthController
import com.example.dsm_centavitos.controller.MovimientoController
import com.example.dsm_centavitos.databinding.ActivityHistorialBinding
import com.example.dsm_centavitos.model.MovimientoExtended

class HistorialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistorialBinding
    private val authController = AuthController()
    private lateinit var movimientoController: MovimientoController
    private lateinit var adapter: MovimientoAdapter
    private var allMovements = listOf<MovimientoExtended>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistorialBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movimientoController = MovimientoController(this)
        setupRecyclerView()
        setupSearch()
        setupFilters()
    }

    override fun onResume() {
        super.onResume()
        loadMovements()
    }

    private fun setupRecyclerView() {
        adapter = MovimientoAdapter(
            listOf(),
            onItemClick = { mov ->
                val intent = Intent(this, MovimientoFormActivity::class.java)
                intent.putExtra("MOVEMENT_ID", mov.id)
                startActivity(intent)
            },
            onItemLongClick = { mov ->
                showDeleteDialog(mov)
            }
        )
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.rvHistory.adapter = adapter
    }

    private fun showDeleteDialog(mov: MovimientoExtended) {
        AlertDialog.Builder(this)
            .setTitle("Eliminar movimiento")
            .setMessage("¿Estás seguro de que deseas eliminar este registro de $${mov.monto}?")
            .setPositiveButton("Eliminar") { _, _ ->
                if (movimientoController.deleteMovimiento(mov.id) > 0) {
                    Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show()
                    loadMovements()
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun loadMovements() {
        val uid = authController.getUid() ?: return
        allMovements = movimientoController.getAllMovimientosExtended(uid)
        
        if (allMovements.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvHistory.visibility = View.VISIBLE
            adapter.updateList(allMovements)
        }
    }

    private fun setupFilters() {
        binding.chipGroupFilters.setOnCheckedStateChangeListener { _, _ ->
            applyFilters()
        }
    }

    private fun applyFilters() {
        val searchText = binding.etSearch.text.toString()
        val selectedChipId = binding.chipGroupFilters.checkedChipId

        val filteredList = allMovements.filter { mov ->
            // Filtro por texto
            val matchesSearch = mov.descripcion?.contains(searchText, ignoreCase = true) == true ||
                    mov.categoriaNombre.contains(searchText, ignoreCase = true)

            // Filtro por tipo (Chip)
            val matchesType = when (selectedChipId) {
                R.id.chipExpenses -> mov.tipo == "GASTO"
                R.id.chipIncomes -> mov.tipo == "INGRESO"
                else -> true // Todos
            }

            matchesSearch && matchesType
        }
        
        adapter.updateList(filteredList)
        
        if (filteredList.isEmpty()) {
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.GONE
        } else {
            binding.tvEmpty.visibility = View.GONE
            binding.rvHistory.visibility = View.VISIBLE
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilters()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }
}
