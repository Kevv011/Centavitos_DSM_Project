package com.example.dsm_centavitos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_centavitos.R
import com.example.dsm_centavitos.databinding.ItemPresupuestoBinding
import com.example.dsm_centavitos.model.PresupuestoExtended

class PresupuestoAdapter(private val presupuestos: List<PresupuestoExtended>) : RecyclerView.Adapter<PresupuestoAdapter.PresupuestoViewHolder>() {

    class PresupuestoViewHolder(val binding: ItemPresupuestoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresupuestoViewHolder {
        val binding = ItemPresupuestoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PresupuestoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PresupuestoViewHolder, position: Int) {
        val item = presupuestos[position]
        holder.binding.tvCatName.text = item.categoriaNombre
        holder.binding.tvLimitAmount.text = "$ %.2f".format(item.montoLimite)
        holder.binding.tvSpentAmount.text = "Gastado: $ %.2f de $ %.2f".format(item.gastoActual, item.montoLimite)
        
        val progreso = if (item.montoLimite > 0) (item.gastoActual / item.montoLimite * 100).toInt() else 0
        holder.binding.progressBudget.progress = progreso
        
        if (progreso >= 100) {
            holder.binding.progressBudget.setIndicatorColor(ContextCompat.getColor(holder.itemView.context, R.color.brand_error))
        } else if (progreso >= 80) {
            // Un color de advertencia si existiera, o usar el primario
            holder.binding.progressBudget.setIndicatorColor(ContextCompat.getColor(holder.itemView.context, R.color.primary))
        } else {
            holder.binding.progressBudget.setIndicatorColor(ContextCompat.getColor(holder.itemView.context, R.color.brand_green))
        }
    }

    override fun getItemCount(): Int = presupuestos.size
}
