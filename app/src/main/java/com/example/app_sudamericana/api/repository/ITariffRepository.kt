package com.example.app_sudamericana.api.repository

import com.example.app_sudamericana.api.domain.Response.*
import com.example.app_sudamericana.utils.enviroments.Credentials
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface ITariffRepository {

    @Headers("Content-Type: application/json")
    @GET(Credentials.URL_TARIFF + "/all")
    fun getAllTariffs(@Header("AUTHORIZATION") token: String): Observable<TariffResponse>



}