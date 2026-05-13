package com.example.dsm_centavitos.model

data class Categoria(
    val id: Int = 0,
    val nombre: String,
    val tipo: String, // "INGRESO" o "GASTO"
    val icono: String? = null,
    val color: String? = null,
    val firebaseUid: String? = null // null para categorías globales
)
