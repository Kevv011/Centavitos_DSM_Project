package com.example.dsm_centavitos

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.controller.AuthController
import com.example.dsm_centavitos.controller.CategoriaController
import com.example.dsm_centavitos.controller.MovimientoController
import com.example.dsm_centavitos.databinding.ActivityMainBinding
import com.example.dsm_centavitos.model.Movimiento
import com.example.dsm_centavitos.ui.PerfilActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val authController = AuthController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToProfile.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }

        binding.btnRunDbTest.setOnClickListener {
            runDbTest()
        }
    }

    private fun runDbTest() {
        val uid = authController.getUid() ?: return
        val catController = CategoriaController(this)
        val movController = MovimientoController(this)

        // 1. Verificar categorías precargadas
        val categorias = catController.getCategorias(null, null)
        
        // 2. Insertar movimiento de prueba
        if (categorias.isNotEmpty()) {
            val primeraCat = categorias[0]
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val fechaActual = sdf.format(Date())

            val nuevoMov = Movimiento(
                firebaseUid = uid,
                tipo = "GASTO",
                monto = 15.50,
                categoriaId = primeraCat.id,
                fecha = fechaActual,
                descripcion = "Prueba de DB: Almuerzo"
            )

            val idMov = movController.insertMovimiento(nuevoMov)

            // 3. Consultar resultados
            val todos = movController.getAllMovimientos(uid)
            
            val mensaje = """
                Test Exitoso:
                - Categorías encontradas: ${categorias.size}
                - Movimiento insertado ID: $idMov
                - Total movimientos en DB: ${todos.size}
                - Último: ${todos.firstOrNull()?.descripcion} ($${todos.firstOrNull()?.monto})
            """.trimIndent()

            Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Error: No hay categorías precargadas", Toast.LENGTH_SHORT).show()
        }
    }
}
