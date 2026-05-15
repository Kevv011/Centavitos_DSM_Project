package com.example.dsm_centavitos.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dsm_centavitos.adapter.NoticiaAdapter
import com.example.dsm_centavitos.controller.NoticiaController
import com.example.dsm_centavitos.databinding.ActivityNoticiasBinding
import com.example.dsm_centavitos.network.RetrofitClient

/**
 * Pantalla para mostrar el listado de noticias financieras.
 * RF12 — Noticias, consejos o artículos mediante API REST
 */
class NoticiasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoticiasBinding
    private lateinit var noticiaController: NoticiaController
    private lateinit var noticiaAdapter: NoticiaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoticiasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupController()
        obtenerNoticias()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarNoticias)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarNoticias.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        noticiaAdapter = NoticiaAdapter(emptyList()) { noticia ->
            abrirArticulo(noticia.url)
        }
        binding.rvNoticias.apply {
            layoutManager = LinearLayoutManager(this@NoticiasActivity)
            adapter = noticiaAdapter
        }
    }

    private fun setupController() {
        val apiService = RetrofitClient.instance
        noticiaController = NoticiaController(apiService)
    }

    private fun obtenerNoticias() {
        binding.pbLoadingNoticias.visibility = View.VISIBLE
        binding.tvErrorNoticias.visibility = View.GONE

        noticiaController.cargarNoticias { noticias ->
            runOnUiThread {
                binding.pbLoadingNoticias.visibility = View.GONE
                if (noticias != null) {
                    if (noticias.isEmpty()) {
                        binding.tvErrorNoticias.text = "No hay noticias disponibles"
                        binding.tvErrorNoticias.visibility = View.VISIBLE
                    } else {
                        noticiaAdapter.updateData(noticias)
                    }
                } else {
                    binding.tvErrorNoticias.visibility = View.VISIBLE
                    Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun abrirArticulo(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "No se pudo abrir el enlace", Toast.LENGTH_SHORT).show()
        }
    }
}
