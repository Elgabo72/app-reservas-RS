package com.example.app_sudamericana.ui.activitys

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.example.app_sudamericana.R
import com.example.app_sudamericana.databinding.ActivityHomeBinding
import com.example.app_sudamericana.ui.fragments.HomeFragment
import com.example.app_sudamericana.ui.fragments.PerfilFragment
import com.example.app_sudamericana.ui.fragments.ReservaFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private val homeFragment = HomeFragment()
    private val perfilFragment = PerfilFragment()
    private val reservaFragment = ReservaFragment()
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RepetirFragment(homeFragment)


        //Boton de navegaciones interface
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.navigation_home -> RepetirFragment(homeFragment)
                R.id.navigation_reserva -> RepetirFragment(reservaFragment)
                R.id.navigation_Perfil -> RepetirFragment(perfilFragment)
            }
            true
        }

        //SharedPreferences cerrar sesión BOTON
        binding.Btncerrarseccion.setOnClickListener { logOut() }


    }



    //SharedPreferences cerrar sesión
    private fun logOut() {
        val sp = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        with(sp.edit()){
            putString("active", "false")
            apply()
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }


//===================================================================================================
    private fun  RepetirFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fl_wrapper, fragment)
        commit()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }



    }
