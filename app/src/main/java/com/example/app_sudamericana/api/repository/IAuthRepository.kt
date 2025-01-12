package com.example.app_sudamericana.api.repository

import com.example.app_sudamericana.api.domain.Authenticate
import com.example.app_sudamericana.api.domain.Response.AuthenticateResponse
import com.example.app_sudamericana.api.domain.Response.UserRegisterResponse
import com.example.app_sudamericana.api.domain.UserRegister
import com.example.app_sudamericana.utils.enviroments.Credentials
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface IAuthRepository {

    @Headers("Content-Type: application/json")
    @POST(Credentials.URL_AUTH+"/register")
    fun registerUser(@Body userData: UserRegister): Observable<UserRegisterResponse>

    @Headers("Content-Type: application/json")
    @POST(Credentials.URL_AUTH+"/authenticatePassengers")
    fun login(@Body userData: Authenticate): Observable<AuthenticateResponse>







}