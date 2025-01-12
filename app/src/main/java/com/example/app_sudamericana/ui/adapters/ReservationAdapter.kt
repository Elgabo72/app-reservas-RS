package com.example.app_sudamericana.ui.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app_sudamericana.api.domain.Response.ReservationResponseItem
import com.example.app_sudamericana.R
import com.example.app_sudamericana.utils.enviroments.Status
import com.google.gson.Gson

class ReservationsAdapter(var context: Context, val reservations: ArrayList<ReservationResponseItem>): RecyclerView.Adapter<ReservationsAdapter.ReservationHolder>() {

    private val items = ArrayList<ReservationResponseItem>()

    fun setItems(items: ArrayList<ReservationResponseItem>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationHolder {
        return ReservationHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_my_post, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    //
    override fun onBindViewHolder(holder: ReservationHolder, position: Int) {
        holder.bind(items[position], context)
    }

    class ReservationHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var mItem : ReservationResponseItem
        //TODO instanciar elementos
        val estatus: TextView = itemView.findViewById(R.id.estatus)
        val description: TextView = itemView.findViewById(R.id.Descripcion)
        val estatusIcono: ImageView = itemView.findViewById(R.id.status_img)
        val origen: TextView = itemView.findViewById(R.id.origen)
        val destino: TextView = itemView.findViewById(R.id.destino)
        val nomchofer: TextView = itemView.findViewById(R.id.pasajeroName)
        val costo: TextView = itemView.findViewById(R.id.costo)



        fun bind(data: ReservationResponseItem, context: Context) {
            Log.wtf("testItem", "---> ${Gson().toJson(data).toString()}")
            this.mItem=data

            estatus.text = data.stateReservation.description
            origen.text = "Origen: ${data.tariff.origin} "
            destino.text = "Destino: ${data.tariff.destination}"
            costo.text = "$. ${data.tariff.amount.toDouble().toString()}"
            description.text = "Descripción: ${data.description}"

            if(data.driver!=null){
                nomchofer.text = "${data.driver.firstName} ${data.driver.lastName}"
            }

            when(data.stateReservation.idStateReservation){
                Status.PENDIENTE -> {
                    estatusIcono.setImageResource(R.drawable.pendiente)

                }
                Status.EN_CAMINO -> {
                    estatusIcono.setImageResource(R.drawable.camino)
                }
                Status.EN_ESPERA -> {

                    estatusIcono.setImageResource(R.drawable.espera)
                }
                Status.EN_VIAJE -> {

                    estatusIcono.setImageResource(R.drawable.viaje)
                }
                Status.VIAJE_REALIZADO -> {

                    estatusIcono.setImageResource(R.drawable.realizado)
                }
                Status.CANCELADO -> {

                    estatusIcono.setImageResource(R.drawable.cacelado)
                }
            }

        }
    }


}





















