package com.example.dsm_centavitos.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.controller.AuthController
import com.example.dsm_centavitos.controller.PerfilController
import com.example.dsm_centavitos.databinding.ActivityPerfilBinding
import com.example.dsm_centavitos.model.Usuario

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private val authController = AuthController()
    private lateinit var perfilController: PerfilController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        perfilController = PerfilController(this)

        val uid = authController.getUid()
        val email = authController.getCurrentUser()?.email ?: ""

        if (uid != null) {
            // Cargar datos actuales si existen
            val perfilActual = perfilController.getPerfil(uid)
            perfilActual?.let {
                binding.etFirstName.setText(it.nombre)
                binding.etLastName.setText(it.apellido)
                binding.etCareer.setText(it.carrera)
                binding.etCurrency.setText(it.moneda)
            }

            binding.btnSaveProfile.setOnClickListener {
                val usuario = Usuario(
                    firebaseUid = uid,
                    nombre = binding.etFirstName.text.toString(),
                    apellido = binding.etLastName.text.toString(),
                    correo = email,
                    carrera = binding.etCareer.text.toString(),
                    moneda = binding.etCurrency.text.toString()
                )

                if (perfilController.saveOrUpdatePerfil(usuario)) {
                    Toast.makeText(this, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            authController.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
