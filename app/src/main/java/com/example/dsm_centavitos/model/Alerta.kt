package com.example.dsm_centavitos.model

data class Alerta(
    val id: Int = 0,
    val firebaseUid: String,
    val presupuestoId: Int?,
    val tipo: String,
    val mensaje: String,
    val fecha: String,
    val estado: String // "LEIDA", "NUEVA"
)
