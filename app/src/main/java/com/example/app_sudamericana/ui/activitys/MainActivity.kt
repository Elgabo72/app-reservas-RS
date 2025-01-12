package com.example.app_sudamericana.ui.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.app_sudamericana.api.domain.Authenticate
import com.example.app_sudamericana.api.domain.Response.AuthenticateResponse
import com.example.app_sudamericana.api.service.AuthService
import com.example.app_sudamericana.R
import com.example.app_sudamericana.databinding.ActivityMainBinding
import com.example.app_sudamericana.utils.enviroments.Credentials
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import www.sanju.motiontoast.MotionToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val disposables: CompositeDisposable = CompositeDisposable()
    var authservice: AuthService = AuthService()

    private lateinit var userID: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var btnlogin: Button
    private lateinit var spInstance: SharedPreferences;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userID = findViewById(R.id.TxtUser)
        password = findViewById(R.id.passwordEditText)
        btnlogin = findViewById(R.id.Btnlogin)

        //Guardar datos
        this.spInstance = getSharedPreferences(Credentials.NAME_PREFERENCES, Context.MODE_PRIVATE)
        checkLogIn()
        binding.Btnlogin.setOnClickListener { rememberUser() }


        UsuarioFocusListener()
        ContraseñaFocusListener()

        //Boton registrarse manda activity_registro
        val botonregistrarse = findViewById<TextView>(R.id.TxtRegistrate)
        botonregistrarse.setOnClickListener {
            val Abrir = Intent(this, RegistroActivity::class.java)
            startActivity(Abrir)
        }



        //btnlogin.setOnClickListener({ createlogin() })

        binding.Btnlogin.setOnClickListener { submitLogin() }
//==================================================================================================
    }

    //recordar usuario
    private fun rememberUser() {
        // Recuperamos el contenido de los textField
        val email = binding.usuarioContainer.editText?.text.toString()
        val password = binding.passwordContainer.editText?.text.toString()

        // Verificamos si los campos no son vacíos
        if (email.isNotEmpty() && password.isNotEmpty()) {
            val checkBox = binding.checkBox
            if (checkBox.isChecked) {
                with(this.spInstance.edit()) {

                    putString("email", email)
                    putString("password", password)
                    putString("active", "true")
                    putString("remember", "true")
                    createlogin()
                    apply()

                }
            } else {
                with(this.spInstance.edit()) {
                    putString("active", "true")
                    putString("remember", "false")

                    apply()
                }
            }
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            // En caso los datos no estén completos mostramos un Toast
            submitLogin()
        }
    }

    //checkLogin
    private fun checkLogIn() {
        if (this.spInstance.getString("active", "") == "true") {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        } else {
            if (this.spInstance.getString("remember", "") == "true") {
                binding.usuarioContainer.editText?.setText(this.spInstance.getString("email", ""))
                binding.passwordContainer.editText?.setText(
                    this.spInstance.getString(
                        "password",
                        ""
                    )
                )
            }
        }
    }


    private fun submitLogin() { //valida si el usuario existe en la db
        if (validarUsuario() == null && validarContraseña() == null){
            createlogin()
        } else { //mensaje error de datos
            binding.usuarioContainer.helperText = validarUsuario()
            binding.passwordContainer.helperText = validarContraseña()
            MotionToast.createColorToast(
                this@MainActivity,
                "Operación Fallida",
                "Complete los campos vacios",
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                null
            )
        }

    }

    //validar Usuario
    private fun UsuarioFocusListener() {
        binding.TxtUser.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.usuarioContainer.helperText = validarUsuario()
            }
        }
    }

    private fun validarUsuario(): String? {
        val nombreText = binding.TxtUser.text.toString()
        if (nombreText.length < 3) {
            return "Escribe tu Usuario"
        }
        return null
    }


    //validar Contraseña
    private fun ContraseñaFocusListener() {
        binding.passwordEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                binding.passwordContainer.helperText = validarContraseña()
            }
        }
    }

    private fun validarContraseña(): String? {
        val nombreText = binding.passwordEditText.text.toString()
        if (nombreText.length < 5) {
            return "Escribe tu Contraseña"
        }
        return null
    }




    // Login API
    private fun createlogin() {
        val sp = this.spInstance;

        authservice.login(Authenticate(password.text.toString(), userID.text.toString()))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<AuthenticateResponse> {
                @Override
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                @Override
                override fun onNext(data: AuthenticateResponse) {
                    Toast.makeText(this@MainActivity,data.user.firstName, Toast.LENGTH_SHORT).show()
                    with(sp.edit()) {
                        putString(Credentials.TOKEN_JWT, data.jwt)
                        putString(Credentials.USER_ID, data.user.idUser.toString())
                        putString(Credentials.USER_USERNAME, data.user.username)
                        putString(Credentials.USER_FIRSTNAME, data.user.firstName)
                        putString(Credentials.USER_LASTNAME, data.user.lastName)
                        putString(Credentials.USER_EMAIL, data.user.email)
                        putString(Credentials.USER_ADDRESS, data.user.address)
                        putString(Credentials.SESSION, "true")
                        putString(Credentials.USER_PASSWORD, password.text.toString())
                        putString(Credentials.USER_TELEFONO, data.user.phone)
                        apply()
                    }
                    startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                }

                @Override
                override fun onError(e: Throwable) {
                    MotionToast.createColorToast(
                        this@MainActivity,
                        "Operación Fallida",
                        "Usuario o Contraseña incorrecta",
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        null
                    )
                    //Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                @Override
                override fun onComplete() {
                    disposables.clear()

                }
            })


        val Textregistrar = findViewById<TextView>(R.id.TxtRegistrate)
        Textregistrar.setOnClickListener {
            val lanzar = Intent(this, RegistroActivity::class.java)
            startActivity(lanzar)
        }
    }
}