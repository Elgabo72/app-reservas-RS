package com.example.app_sudamericana.ui.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.app_sudamericana.api.domain.Response.UserUpdateResponse
import com.example.app_sudamericana.api.domain.dto.UserUpdateDto
import com.example.app_sudamericana.api.service.UserService
import com.example.app_sudamericana.databinding.ActivityEditarPerfilBinding
import com.example.app_sudamericana.utils.enviroments.Credentials
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import www.sanju.motiontoast.MotionToast

class EditarPerfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarPerfilBinding
    var userService: UserService = UserService()
    val disposables: CompositeDisposable = CompositeDisposable()

    private lateinit var spInstance: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        NombreFocusListener()
        ApellidoFocusListener()
        DireccionFocusListener()
        TelefonoFocusListener()
        PasswordFocusListener()

        binding.BtnActualizar.setOnClickListener { submitUpdateUser() }
        // binding.BtnActualizar.setOnClickListener({ updateUser()})


        this.spInstance = getSharedPreferences(Credentials.NAME_PREFERENCES, Context.MODE_PRIVATE)

        cargarDatos()


    }


    private fun submitUpdateUser() {
        if (validarNombre() == null && validarApellido() == null && validarDireccion() == null
            && validarTelefono() == null
            && validarContreña() == null){
            updateUser()
        } else{
            binding.nombreContainer.helperText = validarNombre()
            binding.apellidoContainer.helperText = validarApellido()
            binding.direccionContainer.helperText = validarDireccion()
            binding.telefonoContainer.helperText = validarTelefono()
            binding.paswwordContainer.helperText = validarContreña()
        }
    }


    //validar Nombre
    private fun NombreFocusListener() {
        binding.TxtNombre.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.nombreContainer.helperText = validarNombre()
            }
        }
    }
    private fun validarNombre(): String? {
        val nombreText = binding.TxtNombre.text.toString()
        if(nombreText.length<7)
        {
            return "Escribe tu nombre completo"
        }
        return null
    }

    //validar Apellido
    private fun ApellidoFocusListener() {
        binding.TxtApellido.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.apellidoContainer.helperText = validarApellido()
            }
        }
    }
    private fun validarApellido(): String? {
        val apellidoText = binding.TxtApellido.text.toString()
        if(apellidoText.length<10)
        {
            return "Escribe tu Apellido completo"
        }
        return null
    }


    //validar Direccion
    private fun DireccionFocusListener() {
        binding.TxtDireccion.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.direccionContainer.helperText = validarDireccion()
            }
        }
    }
    private fun validarDireccion(): String? {
        val direccionText = binding.TxtDireccion.text.toString()
        if(direccionText.length<5)
        {
            return "Escribe tu Dirección"
        }
        return null
    }

    //validar Telefono
    private fun TelefonoFocusListener() {
        binding.TxtTelefono.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.telefonoContainer.helperText = validarTelefono()
            }
        }
    }
    private fun validarTelefono(): String? {
        val TelefonoText = binding.TxtTelefono.text.toString()
        if(!TelefonoText.matches(".*[0-9].*".toRegex()))
        {
            return "Debe tener 9 dígitos"
        }
        if(TelefonoText.length != 9)
        {
            return "Debe tener 9 dígitos"
        }
        return null
    }

    //validar contraseña
    private fun PasswordFocusListener() {
        binding.TxtPassword.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.paswwordContainer.helperText = validarContreña()
            }
        }
    }
    private fun validarContreña(): String? {
        val passwordText = binding.TxtPassword.text.toString()
        if(passwordText.length<8)
        {
            return "Contraseña mínima de 8 caracteres"
        }
        if(!passwordText.matches(".*[A-Z].*".toRegex()))
        {
            return "Debe contener 1 carácter en mayúscula"
        }
        if(!passwordText.matches(".*[a-z].*".toRegex()))
        {
            return "\n" +
                    "Debe contener 1 carácter en minúscula"
        }
        if(!passwordText.matches(".*[@#\$%^&+=].*".toRegex()))
        {
            return "Debe contener 1 carácter especial especial (@#\$%^&+=)"
        }
        return null
    }







    //SharedPreferences cerrar sesión
    private fun logOut() {
        val sp = getSharedPreferences(Credentials.NAME_PREFERENCES, Context.MODE_PRIVATE)
        with(sp.edit()){
            putString("active", "false")
            apply()
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

private fun cargarDatos(){
    val nombre = this.spInstance.getString(Credentials.USER_FIRSTNAME, "");
    val apellido = this.spInstance.getString(Credentials.USER_LASTNAME, "");
    val direccion = this.spInstance.getString(Credentials.USER_ADDRESS, "");
    val telefono = this.spInstance.getString(Credentials.USER_TELEFONO, "");
    val password = this.spInstance.getString(Credentials.USER_PASSWORD, "");
    binding.TxtNombre.setText(nombre)
    binding.TxtApellido.setText(apellido)
    binding.TxtDireccion.setText(direccion)
    binding.TxtTelefono.setText(telefono)
    binding.TxtPassword.setText(password)

}


    private fun updateUser() {
        val token = this.spInstance.getString(Credentials.TOKEN_JWT, "");
        val userID = this.spInstance.getString(Credentials.USER_ID, 1.toString());
        val email = this.spInstance.getString(Credentials.USER_EMAIL, "");
        val user = this.spInstance.getString(Credentials.USER_USERNAME, "");
        if (token  != null && email!= null && userID!= null && user!= null) {
            var userUpdate = UserUpdateDto(
                true,
              binding.TxtDireccion.getText().toString(),
                email,
                binding.TxtNombre.getText().toString(),
                userID.toString().toInt(),
               binding.TxtApellido.getText().toString(),
                binding.TxtPassword.getText().toString(),
                binding.TxtTelefono.getText().toString(),
                user,
            );
            userService.updateUser(token, userUpdate)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<UserUpdateResponse> {


                    override fun onNext(t: UserUpdateResponse) {
                        MotionToast.createColorToast(
                            this@EditarPerfilActivity,
                            "Operación Exitosa",
                            "Usuario Registrado correctamente",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION ,
                            null
                        )

                        this@EditarPerfilActivity.finish()
                        logOut()

                    }

                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText(
                            this@EditarPerfilActivity,
                            e.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    override fun onComplete() {
                       disposables.clear()

                    }
                })


        }
    }

}

