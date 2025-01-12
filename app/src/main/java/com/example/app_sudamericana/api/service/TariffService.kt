package com.example.app_sudamericana.api.service

import com.example.app_sudamericana.api.domain.Response.*
import com.example.app_sudamericana.api.repository.ITariffRepository
import com.example.app_sudamericana.api.RetrofitInstance
import io.reactivex.rxjava3.core.Observable

class TariffService {
    private val repository: ITariffRepository

    fun getAllTariffs(token: String): Observable<TariffResponse> {
        return repository.getAllTariffs("Bearer ${token}");
    }

    init {
        repository = RetrofitInstance.instance?.createRepository(ITariffRepository::class.java)
            ?: throw IllegalStateException()
    }

}