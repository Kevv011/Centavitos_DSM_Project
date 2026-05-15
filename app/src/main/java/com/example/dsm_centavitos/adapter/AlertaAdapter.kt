package com.example.dsm_centavitos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dsm_centavitos.databinding.ItemAlertaBinding
import com.example.dsm_centavitos.model.Alerta

class AlertaAdapter(private val alertas: List<Alerta>) : RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder>() {

    class AlertaViewHolder(val binding: ItemAlertaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertaViewHolder {
        val binding = ItemAlertaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlertaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlertaViewHolder, position: Int) {
        val alerta = alertas[position]
        holder.binding.tvAlertMessage.text = alerta.mensaje
        holder.binding.tvAlertDate.text = alerta.fecha
        
        // Cambiar icono según tipo si es necesario
        if (alerta.tipo == "EXCEDIDO") {
            holder.binding.ivAlertIcon.setImageResource(android.R.drawable.ic_delete)
        }
    }

    override fun getItemCount(): Int = alertas.size
}
