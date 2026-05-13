package com.example.dsm_centavitos.model

data class Presupuesto(
    val id: Int = 0,
    val firebaseUid: String,
    val categoriaId: Int,
    val montoLimite: Double,
    val mes: Int,
    val anio: Int
)
