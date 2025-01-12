package com.example.app_sudamericana.api.service

import com.example.app_sudamericana.api.domain.Response.UserUpdateResponse
import com.example.app_sudamericana.api.domain.dto.UserUpdateDto
import com.example.app_sudamericana.api.repository.IUserRepository
import com.example.app_sudamericana.api.RetrofitInstance
import io.reactivex.rxjava3.core.Observable

class UserService {
    private val repository: IUserRepository

    fun updateUser(token: String, data: UserUpdateDto): Observable<UserUpdateResponse> {
        return repository.updateUser("Bearer ${token}", data);
    }

    init {
        repository = RetrofitInstance.instance?.createRepository(IUserRepository::class.java)
            ?: throw IllegalStateException()
    }

}