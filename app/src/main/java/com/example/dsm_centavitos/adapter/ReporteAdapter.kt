package com.example.dsm_centavitos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_centavitos.databinding.ItemReporteCategoriaBinding
import com.example.dsm_centavitos.model.ReporteCategoria

class ReporteAdapter(private val items: List<ReporteCategoria>) : RecyclerView.Adapter<ReporteAdapter.ReporteViewHolder>() {

    class ReporteViewHolder(val binding: ItemReporteCategoriaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteViewHolder {
        val binding = ItemReporteCategoriaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReporteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReporteViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvCategoryName.text = item.nombre
        holder.binding.tvCategoryAmount.text = "$ %.2f".format(item.monto)
    }

    override fun getItemCount(): Int = items.size
}
