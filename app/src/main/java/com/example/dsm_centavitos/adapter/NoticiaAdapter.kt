package com.example.dsm_centavitos.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dsm_centavitos.databinding.ItemNoticiaBinding
import com.example.dsm_centavitos.model.Noticia

/**
 * Adaptador para mostrar la lista de noticias en un RecyclerView.
 */
class NoticiaAdapter(
    private var noticias: List<Noticia>,
    private val onItemClick: (Noticia) -> Unit
) : RecyclerView.Adapter<NoticiaAdapter.NoticiaViewHolder>() {

    class NoticiaViewHolder(val binding: ItemNoticiaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val binding = ItemNoticiaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoticiaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val noticia = noticias[position]
        with(holder.binding) {
            tvTituloNoticia.text = noticia.titulo
            tvResumenNoticia.text = noticia.resumen
            tvFechaNoticia.text = noticia.fecha

            Glide.with(ivNoticia.context)
                .load(noticia.imagenUrl)
                .placeholder(android.R.drawable.ic_menu_report_image)
                .error(android.R.drawable.ic_menu_report_image)
                .into(ivNoticia)

            root.setOnClickListener { onItemClick(noticia) }
        }
    }

    override fun getItemCount(): Int = noticias.size

    fun updateData(newNoticias: List<Noticia>) {
        noticias = newNoticias
        notifyDataSetChanged()
    }
}
