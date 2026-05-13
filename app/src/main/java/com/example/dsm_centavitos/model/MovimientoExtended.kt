package com.example.dsm_centavitos.model

data class MovimientoExtended(
    val id: Int,
    val firebaseUid: String,
    val tipo: String,
    val monto: Double,
    val categoriaId: Int,
    val fecha: String,
    val metodoPago: String?,
    val descripcion: String?,
    val categoriaNombre: String,
    val categoriaColor: String?,
    val categoriaIcono: String?
)
