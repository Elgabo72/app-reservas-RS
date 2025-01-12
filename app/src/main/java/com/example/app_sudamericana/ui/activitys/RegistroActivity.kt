package com.example.app_sudamericana.ui.activitys



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.example.app_sudamericana.api.domain.Authenticate
import com.example.app_sudamericana.api.domain.Response.UserRegisterResponse
import com.example.app_sudamericana.api.domain.UserRegister
import com.example.app_sudamericana.api.service.AuthService
import com.example.app_sudamericana.R
import com.example.app_sudamericana.databinding.ActivityRegistroBinding
import com.google.android.material.textfield.TextInputEditText
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registro.*
import www.sanju.motiontoast.MotionToast

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding

      var authservice: AuthService = AuthService()
    val disposables: CompositeDisposable = CompositeDisposable()

    private lateinit var address: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var firstName: TextInputEditText
    private lateinit var lastName: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var phone: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var btnRegisterUser: Button


//======================================================
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
//======================================================
        authservice.login(Authenticate("fdsfsd","fdsfdsfdsf"))
        NombreFocusListener()
        ApellidoFocusListener()
        DireccionFocusListener()
        CorreoFocusListener()
        TelefonoFocusListener()
        UsuarioFocusListener()
        PasswordFocusListener()

        binding.BtnRegisterUser.setOnClickListener { submitFormulario() }

        address = findViewById(R.id.TxtDireccion)
        email = findViewById(R.id.TxtCorreo)
        firstName = findViewById(R.id.TxtNombre)
        lastName = findViewById(R.id.TxtApellido)
        password = findViewById(R.id.TxtPassword)
        phone    = findViewById(R.id.TxtTelefono)
        username = findViewById(R.id.TxtUsuario)
        btnRegisterUser = findViewById(R.id.BtnRegisterUser)

    }


   //Validar formulario click boton registrar
    private fun submitFormulario() {
       if (validarNombre() == null && validarApellido() == null && validarDireccion() == null
           && validarCorreo() == null && validarTelefono() == null && validarUsuario() == null
           && validarContreña() == null){
           createUser()
       } else{
           binding.nombreContainer.helperText = validarNombre()
           binding.apellidoContainer.helperText = validarApellido()
           binding.direccionContainer.helperText = validarDireccion()
           binding.emailContainer.helperText = validarCorreo()
           binding.telefonoContainer.helperText = validarTelefono()
           binding.usuarioContainer.helperText = validarUsuario()
           binding.paswwordContainer.helperText = validarContreña()
           MotionToast.createColorToast(
               this@RegistroActivity,
               "Operación Fallida",
               "Complete los campos vacios",
               MotionToast.TOAST_WARNING,
               MotionToast.GRAVITY_BOTTOM,
               MotionToast.LONG_DURATION,
               null
           )
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
        if(nombreText.length<3)
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

    //validar correo
    private fun CorreoFocusListener() {
        binding.TxtCorreo.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.emailContainer.helperText = validarCorreo()
            }
        }
    }
    private fun validarCorreo(): CharSequence? {
        val emailText = binding.TxtCorreo.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches())
        {
            return "Dirección de correo electrónico inválida"
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


    //validar Usuario
    private fun UsuarioFocusListener() {
        binding.TxtUsuario.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.usuarioContainer.helperText = validarUsuario()
            }
        }
    }
    private fun validarUsuario(): String? {
        val usuariotext = binding.TxtUsuario.text.toString()
        if(usuariotext.length<3)
        {
            return "Escribe tu Usuario"
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








   // Crear un usuario API
    private fun createUser(){
        authservice.registerUser(UserRegister(address.text.toString(), email.text.toString(), firstName.text.toString(),1,lastName.text.toString()
            ,password.text.toString(),phone.text.toString(), username.text.toString()))
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<UserRegisterResponse> {
                @Override
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)

                }

                @Override
                override fun onNext(news: UserRegisterResponse) {
                    MotionToast.createColorToast(
                        this@RegistroActivity,
                        "Operación Exitosa",
                        "Usuario Registrado correctamente",
                        MotionToast.TOAST_SUCCESS,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION ,
                        null
                    )
                    this@RegistroActivity.finish()
                }

                @Override
                override fun onError(e: Throwable) {
                    MotionToast.createColorToast(
                        this@RegistroActivity,
                        "Operación Fallida",
                        "No se pudo registrar el usuario",
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        null
                    )
                }

                @Override
                override fun onComplete() {
                    disposables.clear()
                }
            })
    }

}


