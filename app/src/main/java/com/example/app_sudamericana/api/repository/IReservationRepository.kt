package com.example.app_sudamericana.api.repository

import com.example.app_sudamericana.api.domain.RegisterReservation
import com.example.app_sudamericana.api.domain.Response.RegisterReservationResponse
import com.example.app_sudamericana.api.domain.Response.ReservationResponse
import com.example.app_sudamericana.utils.enviroments.Credentials
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface IReservationRepository {

    @Headers("Content-Type: application/json")
    @GET(Credentials.URL_RESERVATION + "/all")
    fun getAllReservations(@Header("AUTHORIZATION") token: String): Observable<ReservationResponse>

    @Headers("Content-Type: application/json")
    @GET(Credentials.URL_RESERVATION + "/findByIdPassenger/{idPassenger}")
    fun findByIdUser(
        @Header("AUTHORIZATION") token: String,
        @Path("idPassenger") idPassenger: Int
    ): Observable<ReservationResponse>

    @Headers("Content-Type: application/json")
    @POST(Credentials.URL_RESERVATION)
    fun reservation(
        @Header("AUTHORIZATION") token: String,
        @Body userData: RegisterReservation
    ):
            Observable<RegisterReservationResponse>
}