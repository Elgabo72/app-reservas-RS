package com.example.app_sudamericana.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.app_sudamericana.api.domain.RegisterReservation
import com.example.app_sudamericana.api.domain.Response.RegisterReservationResponse
import com.example.app_sudamericana.api.domain.Response.TariffResponse
import com.example.app_sudamericana.api.service.ReservationService
import com.example.app_sudamericana.api.service.TariffService
import com.example.app_sudamericana.R
import com.example.app_sudamericana.databinding.FragmentReservaBinding
import com.example.app_sudamericana.ui.activitys.HomeActivity
import com.example.app_sudamericana.ui.activitys.MainActivity
import com.example.app_sudamericana.utils.enviroments.Credentials
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import www.sanju.motiontoast.MotionToast
import kotlin.coroutines.coroutineContext


class ReservaFragment : Fragment() {
    val disposables: CompositeDisposable = CompositeDisposable()

    var reservation: ReservationService = ReservationService()
    private lateinit var spInstance: SharedPreferences;

    private var _binding: FragmentReservaBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePicker: MaterialTimePicker
    var dateCurrentPicker: String = "";
    var timeCurrentPicker: String = "";
    var tariffService = TariffService();
    lateinit var tariffList: TariffResponse;
    var idTariffSelected: Int = 0;


    @SuppressLint("SetTextI18n")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        _binding = FragmentReservaBinding.inflate(inflater, container, false)
        registroReserva()
        registroDescripcion()

        this.spInstance = requireActivity().getSharedPreferences(
            Credentials.NAME_PREFERENCES,
            Context.MODE_PRIVATE
        );

        //Boton solicitar reserva
        binding.BtnSolicitar.setOnClickListener { submitRegistroReserva() }


        binding.selectTarifas.setOnItemClickListener(AdapterView.OnItemClickListener { parent, view, position, id ->
            var tariffSelect = tariffList[position];
            this.idTariffSelected = tariffSelect.idTariff;
            binding.txtMontoTarifa.setText(
                "S./ ${
                    tariffSelect.amount.toString().toDouble()
                }"
            );

        })
        this.cargarTarifas()

        return binding.root




    }

    //Valida los campos y reservas
    private fun submitRegistroReserva() {
        if (validarRegistro() == null && validarDescripcion() == null){
            reservation()
        } else{
            binding.TxtOrigenDestino.helperText = validarRegistro()
            //binding.direccionContainer.helperText = validarDescripcion()
            MotionToast.createColorToast(
                this@ReservaFragment.requireActivity(),
                "Operación Fallida",
                "Complete los campos vacios",
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                null
            )
        }
    }

    //validar ListaOrigenyDestino
    private fun registroReserva() {
        binding.selectTarifas.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.TxtOrigenDestino.helperText = validarRegistro()
            }
        }
    }
    private fun validarRegistro(): String? {
        val nombreText = binding.selectTarifas.text.toString()
        if(nombreText.length<3)
        {
            return "Selecciona tu origen y destino"
        }
        return null
    }

    //validar Descripcion
    private fun registroDescripcion() {
        binding.TxtDescripcion.setOnFocusChangeListener { _, focused ->
            if(!focused)
            {
                binding.direccionContainer.helperText = validarDescripcion()
            }
        }
    }
    private fun validarDescripcion(): String? {
        val nombreText = binding.selectTarifas.text.toString()
        if(nombreText.length<10)
        {
            return "Dirección de recojo"
        }
        return null
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentReservaBinding.bind(view)


        //Funcion Hora y fecha
        binding.apply {
            //Funcion Hora
            btnSelectDate.setOnClickListener {
                // crear una nueva instancia de DatePickerFragment
                val datePickerFragment = DatePickerFragment()
                val supportFragmentManager = requireActivity().supportFragmentManager
                //tenemos que implementar setFragmentResultListener
                supportFragmentManager.setFragmentResultListener(
                    "REQUEST_KEY", viewLifecycleOwner
                ) { resultKey, bundle ->
                    if (resultKey == "REQUEST_KEY") {
                        val date = bundle.getString("SELECTED_DATE")
                        tvSelectedDate.text = date
                        val dateSplit = date.toString().split("-");
                        val ano = dateSplit[2];
                        val month = dateSplit[1];
                        val day = dateSplit[0];
                        dateCurrentPicker = "${ano}-${month}-${day}";

                    }
                }

                // show
                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
            }

            //Funcion Hora
            BtnSelectHora.setOnClickListener {
                datePicker.show(childFragmentManager, "tag");
            }
            datePicker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(12)
                    .setMinute(10)
                    .build()
            timePickerCallback()
        }


    }


    //Funcion Hora
    fun timePickerCallback() {
        datePicker?.let { mDatePicker ->
            mDatePicker.addOnPositiveButtonClickListener {
                Log.wtf("timePicker1", mDatePicker.hour.toString())
                var format = if (mDatePicker.hour >= 13) {
                    "PM"
                } else {
                    "AM"
                }
                binding.TxtHora.text = "${mDatePicker.hour} : ${mDatePicker.minute} $format"


                timeCurrentPicker =
                    "${mDatePicker.hour}:${mDatePicker.minute}:00"
            }
            mDatePicker.addOnNegativeButtonClickListener {
               // Log.wtf("timePicker2", mDatePicker.hour.toString())

            }
            mDatePicker.addOnCancelListener {
                //    Log.wtf("timePicker3",mDatePicker.hour.toString())
            }
            mDatePicker.addOnDismissListener {
                //    Log.wtf("timePicker4",mDatePicker.hour.toString())
            }
        }

    }



    //Crear reservas
    private fun reservation() {
        val token = this.spInstance.getString(Credentials.TOKEN_JWT, "");
        val userID = this.spInstance.getString(Credentials.USER_ID, 1.toString());

        if (token != null && userID != null) {
            reservation.seveReservation(
                token, RegisterReservation(
                    binding.TxtDescripcion.getText().toString(),
                    userID.toString().toInt(),
                    1,
                    this.idTariffSelected, "${dateCurrentPicker}T${timeCurrentPicker}"
                )
            )
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<RegisterReservationResponse> {

                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                    }

                    override fun onNext(t: RegisterReservationResponse) {
                       // Toast.makeText(context, "Registrado Correctamente", Toast.LENGTH_LONG).show()
                        MotionToast.createColorToast(
                            this@ReservaFragment.requireActivity(),
                            "Operación Exitosa",
                            "Registrado Correctamente",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION ,
                            null
                        )
                        startActivity(Intent(context, HomeActivity::class.java))
                    }

                    override fun onError(e: Throwable) {
                        Toast.makeText( context,e.message,Toast.LENGTH_LONG).show()
                    }

                    override fun onComplete() {
                        disposables.clear()
                    }
                })
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun cargarDataSelect(data: TariffResponse) {
        val entries: List<String> = data.toList().map { "${it.origin} - ${it.destination}" };
        //Creación del adapter
        val adapter = context?.let {
            ArrayAdapter(
                it, // Contexto
                R.layout.list_item, //Layout del diseño
                entries //Array
            )
        }
        //Agregamos el adapter al autoCompleteTextView
        with(binding.selectTarifas) {
            setAdapter(adapter)
        }
    }

    fun cargarTarifas() {
        val token = this.spInstance.getString(Credentials.TOKEN_JWT, "");
        if (token != null) {
            tariffService.getAllTariffs(token).subscribeOn(
                Schedulers.io()
            ).observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<TariffResponse> {
                    override fun onSubscribe(d: Disposable) {
                        disposables.add(d)
                    }

                    override fun onNext(t: TariffResponse) {
                       // Toast.makeText(context, "Se cargaron las tarifas", Toast.LENGTH_SHORT).show()
                        MotionToast.createColorToast(
                            this@ReservaFragment.requireActivity(),
                            "Operación Exitosa",
                            "Se cargaron las tarifas",
                            MotionToast.TOAST_INFO,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION ,
                            null
                        )

                        tariffList = t;
                        cargarDataSelect(t)
                    }

                    override fun onError(e: Throwable) {
                        if (e.message.toString().equals(Credentials.HTTP403)) {
                            Toast.makeText(
                                context,
                                "BORRAR LA SESSION Y VOLVER A INICIAR",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            disposables.clear()
                           // Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onComplete() {
                        disposables.clear()

                    }

                })
        }
    }
}