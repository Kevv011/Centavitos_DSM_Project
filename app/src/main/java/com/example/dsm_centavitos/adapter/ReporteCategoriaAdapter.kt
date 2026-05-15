package com.example.dsm_centavitos.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_centavitos.databinding.ItemReporteCategoriaBinding
import java.text.NumberFormat
import java.util.*

data class CategoriaReporte(
    val nombre: String,
    val monto: Double,
    val porcentaje: Int,
    val color: String?
)

class ReporteCategoriaAdapter(
    private val items: List<CategoriaReporte>
) : RecyclerView.Adapter<ReporteCategoriaAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemReporteCategoriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReporteCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvCatNombre.text = item.nombre
            tvMontoTotal.text = NumberFormat.getCurrencyInstance(Locale("en", "US")).format(item.monto)
            tvPorcentajeLabel.text = "${item.porcentaje}%"
            progressPorcentaje.progress = item.porcentaje
            
            item.color?.let {
                try {
                    viewCatColor.background.setTint(Color.parseColor(it))
                    progressPorcentaje.setIndicatorColor(Color.parseColor(it))
                } catch (e: Exception) { }
            }
        }
    }

    override fun getItemCount() = items.size
}
