package com.example.dsm_centavitos.network

import com.example.dsm_centavitos.model.Noticia
import retrofit2.Call
import retrofit2.http.GET

/**
 * Interfaz que define los endpoints de la API REST para el módulo de noticias.
 */
interface ApiService {
    @GET("news")
    fun obtenerNoticias(): Call<List<Noticia>>
}
