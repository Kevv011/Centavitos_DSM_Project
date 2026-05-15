package com.example.dsm_centavitos.controller

import com.example.dsm_centavitos.model.Noticia
import com.example.dsm_centavitos.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Controlador para la gestión de noticias financieras.
 * Coordina la obtención de datos desde la API y la entrega a la vista.
 */
class NoticiaController(private val apiService: ApiService) {

    /**
     * Obtiene la lista de noticias desde la API de forma asíncrona.
     * @param onResult Callback que retorna la lista de noticias o null en caso de error.
     */
    fun cargarNoticias(onResult: (List<Noticia>?) -> Unit) {
        apiService.obtenerNoticias().enqueue(object : Callback<List<Noticia>> {
            override fun onResponse(call: Call<List<Noticia>>, response: Response<List<Noticia>>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<List<Noticia>>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
