package com.example.app_sudamericana.ui.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.app_sudamericana.R

class PrincipalActivity : AppCompatActivity() {
    private lateinit var spInstance: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        this.spInstance = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        if (this.spInstance.getString("active", "") == "true") {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }


        val botonlogin = findViewById<Button>(R.id.Btnregistrarse)
        botonlogin.setOnClickListener {
            val lanzar = Intent(this, RegistroActivity::class.java)
            startActivity(lanzar)
        }

        val botonregistrar = findViewById<Button>(R.id.Btnlogin)
        botonregistrar.setOnClickListener {
            val Abrir = Intent(this, MainActivity::class.java)
            startActivity(Abrir)
        }


    }
}