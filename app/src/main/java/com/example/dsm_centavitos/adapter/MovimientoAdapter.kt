package com.example.dsm_centavitos.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_centavitos.R
import com.example.dsm_centavitos.databinding.ItemMovimientoBinding
import com.example.dsm_centavitos.model.MovimientoExtended

class MovimientoAdapter(
    private var movimientos: List<MovimientoExtended>,
    private val onItemClick: (MovimientoExtended) -> Unit,
    private val onItemLongClick: (MovimientoExtended) -> Unit
) : RecyclerView.Adapter<MovimientoAdapter.MovimientoViewHolder>() {

    class MovimientoViewHolder(val binding: ItemMovimientoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimientoViewHolder {
        val binding = ItemMovimientoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovimientoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovimientoViewHolder, position: Int) {
        val mov = movimientos[position]
        with(holder.binding) {
            tvCategoryName.text = mov.categoriaNombre
            tvDescription.text = if (mov.descripcion.isNullOrEmpty()) "Sin descripción" else mov.descripcion
            tvDate.text = mov.fecha
            tvAmount.text = String.format("$ %.2f", mov.monto)

            // Color del monto según tipo
            if (mov.tipo == "INGRESO") {
                tvAmount.setTextColor(ContextCompat.getColor(root.context, R.color.brand_green_deep))
            } else {
                tvAmount.setTextColor(ContextCompat.getColor(root.context, R.color.brand_error))
            }

            // Color de la categoría
            mov.categoriaColor?.let {
                try {
                    viewCategoryIcon.background.setTint(Color.parseColor(it))
                } catch (e: Exception) {
                    viewCategoryIcon.background.setTint(ContextCompat.getColor(root.context, R.color.brand_green))
                }
            }

            root.setOnClickListener { onItemClick(mov) }
            root.setOnLongClickListener {
                onItemLongClick(mov)
                true
            }
        }
    }

    override fun getItemCount() = movimientos.size

    fun updateList(newList: List<MovimientoExtended>) {
        movimientos = newList
        notifyDataSetChanged()
    }
}
