package com.example.app_sudamericana.api.service

import com.example.app_sudamericana.api.domain.RegisterReservation
import com.example.app_sudamericana.api.domain.Response.RegisterReservationResponse
import com.example.app_sudamericana.api.domain.Response.ReservationResponse
import com.example.app_sudamericana.api.repository.IReservationRepository
import com.example.app_sudamericana.api.RetrofitInstance
import io.reactivex.rxjava3.core.Observable

class ReservationService {
    private val repository: IReservationRepository

    fun getAllReservations(token: String): Observable<ReservationResponse> {
        return repository.getAllReservations("Bearer ${token}");
    }

    fun findByIdUser(token: String, idPassenger: Int): Observable<ReservationResponse> {
        return repository.findByIdUser("Bearer ${token}", idPassenger);
    }

    fun seveReservation(
        token: String,
        data: RegisterReservation
    ): Observable<RegisterReservationResponse> {
        return repository.reservation("Bearer ${token}", data);
    }


    init {
        repository = RetrofitInstance.instance?.createRepository(IReservationRepository::class.java)
            ?: throw IllegalStateException()
    }

}