package com.example.dsm_centavitos.model

data class Usuario(
    val firebaseUid: String,
    val nombre: String? = null,
    val apellido: String? = null,
    val correo: String,
    val carrera: String? = null,
    val moneda: String? = "USD"
)
