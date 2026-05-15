package com.example.dsm_centavitos.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_centavitos.R
import com.example.dsm_centavitos.databinding.ItemPresupuestoBinding
import com.example.dsm_centavitos.model.Presupuesto
import com.example.dsm_centavitos.controller.CategoriaController
import com.example.dsm_centavitos.controller.MovimientoController
import java.text.NumberFormat
import java.util.*

class PresupuestoAdapter(
    private var presupuestos: List<Presupuesto>,
    private val context: Context,
    private val onItemClick: (Presupuesto) -> Unit,
    private val onItemLongClick: (Presupuesto) -> Unit
) : RecyclerView.Adapter<PresupuestoAdapter.PresupuestoViewHolder>() {

    private val categoriaController = CategoriaController(context)
    private val movimientoController = MovimientoController(context)

    class PresupuestoViewHolder(val binding: ItemPresupuestoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresupuestoViewHolder {
        val binding = ItemPresupuestoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PresupuestoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PresupuestoViewHolder, position: Int) {
        val pre = presupuestos[position]
        val uid = pre.firebaseUid
        
        // Obtener info de categoría
        val categorias = categoriaController.getCategorias(null, uid)
        val categoria = categorias.find { it.id == pre.categoriaId }
        
        // Obtener gasto actual del mes para esta categoría
        val movimientos = movimientoController.getAllMovimientosExtended(uid).filter { mov ->
            val dateParts = mov.fecha.split("-")
            if (dateParts.size >= 2) {
                val m = dateParts[1].toIntOrNull() ?: 0
                val a = dateParts[0].toIntOrNull() ?: 0
                m == pre.mes && a == pre.anio && mov.categoriaId == pre.categoriaId
            } else false
        }
        val gastoActual = movimientos.sumOf { it.monto }
        val porcentaje = if (pre.montoLimite > 0) (gastoActual / pre.montoLimite * 100).toInt() else 0

        with(holder.binding) {
            tvCategoryName.text = categoria?.nombre ?: "Sin categoría"
            tvMontoLimite.text = formatCurrency(pre.montoLimite)
            tvGastoActual.text = formatCurrency(gastoActual)
            tvPresupuestoStatus.text = context.getString(R.string.consumido_status, porcentaje)
            
            progressPresupuesto.progress = porcentaje
            
            // Cambiar color de la barra según el porcentaje
            when {
                porcentaje >= 100 -> {
                    progressPresupuesto.setIndicatorColor(ContextCompat.getColor(context, R.color.brand_error))
                }
                porcentaje >= 80 -> {
                    progressPresupuesto.setIndicatorColor(Color.parseColor("#FFC107")) // Warning yellow
                }
                else -> {
                    progressPresupuesto.setIndicatorColor(ContextCompat.getColor(context, R.color.brand_green))
                }
            }

            // Color del círculo de categoría
            categoria?.color?.let {
                try {
                    viewCategoryColor.background.setTint(Color.parseColor(it))
                } catch (e: Exception) {
                    viewCategoryColor.background.setTint(ContextCompat.getColor(context, R.color.brand_green))
                }
            }

            root.setOnClickListener { onItemClick(pre) }
            root.setOnLongClickListener {
                onItemLongClick(pre)
                true
            }
        }
    }

    override fun getItemCount() = presupuestos.size

    private fun formatCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("en", "US")).format(amount)
    }

    fun updateList(newList: List<Presupuesto>) {
        presupuestos = newList
        notifyDataSetChanged()
    }
}
