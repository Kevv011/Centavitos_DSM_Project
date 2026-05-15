package com.example.dsm_centavitos.model

data class PresupuestoExtended(
    val id: Int,
    val firebaseUid: String,
    val categoriaId: Int,
    val montoLimite: Double,
    val mes: Int,
    val anio: Int,
    val categoriaNombre: String,
    val gastoActual: Double
)
