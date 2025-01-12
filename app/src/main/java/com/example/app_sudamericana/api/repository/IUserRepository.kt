package com.example.app_sudamericana.api.repository

import com.example.app_sudamericana.api.domain.Response.UserUpdateResponse
import com.example.app_sudamericana.api.domain.dto.UserUpdateDto
import com.example.app_sudamericana.utils.enviroments.Credentials
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.*

interface IUserRepository {

    @Headers("Content-Type: application/json")
    @PUT(Credentials.URL_USER + "/updatePassenger")
    fun updateUser(
        @Header("AUTHORIZATION") token: String,
        @Body data: UserUpdateDto
    ): Observable<UserUpdateResponse>

}