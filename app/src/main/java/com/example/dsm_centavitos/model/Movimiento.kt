package com.example.dsm_centavitos.model

data class Movimiento(
    val id: Int = 0,
    val firebaseUid: String,
    val tipo: String, // "INGRESO" o "GASTO"
    val monto: Double,
    val categoriaId: Int,
    val fecha: String,
    val metodoPago: String? = null,
    val descripcion: String? = null,
    val createdAt: String? = null
)
