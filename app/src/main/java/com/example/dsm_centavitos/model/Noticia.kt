package com.example.dsm_centavitos.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo de datos para las noticias financieras consumidas desde MockAPI.
 * RF12 — Noticias, consejos o artículos mediante API REST
 */
data class Noticia(
    @SerializedName("id")
    val id: String,
    
    @SerializedName("titulo")
    val titulo: String,
    
    @SerializedName("resumen")
    val resumen: String,
    
    @SerializedName("fecha")
    val fecha: String,
    
    @SerializedName("url")
    val url: String,
    
    @SerializedName("imagenUrl")
    val imagenUrl: String
)
