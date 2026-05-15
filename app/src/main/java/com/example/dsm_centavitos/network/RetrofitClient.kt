package com.example.dsm_centavitos.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton para configurar y proveer la instancia de Retrofit.
 */
object RetrofitClient {
    private const val BASE_URL = "https://6a06b820c83ba8ad9b3dc39e.mockapi.io/api/v1/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}
