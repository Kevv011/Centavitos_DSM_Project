package com.example.dsm_centavitos

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dsm_centavitos.databinding.ActivityMainBinding
import com.example.dsm_centavitos.ui.PerfilActivity

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToProfile.setOnClickListener {
            startActivity(Intent(this, PerfilActivity::class.java))
        }
    }
}
