package com.example.app_sudamericana.api.service

import com.example.app_sudamericana.api.domain.Authenticate
import com.example.app_sudamericana.api.domain.Response.AuthenticateResponse
import com.example.app_sudamericana.api.domain.Response.UserRegisterResponse
import com.example.app_sudamericana.api.domain.UserRegister
import com.example.app_sudamericana.api.repository.IAuthRepository
import com.example.app_sudamericana.api.RetrofitInstance
import io.reactivex.rxjava3.core.Observable

class AuthService {
    private val repository: IAuthRepository
   // val news: Observable<List<New>>
   //     get() = repository.getNews()


    fun registerUser(userData: UserRegister): Observable<UserRegisterResponse>{
            return repository.registerUser(userData)
    }



    fun login(userData: Authenticate): Observable<AuthenticateResponse>{
        return repository.login(userData)
    }



    init {
        repository = RetrofitInstance.instance?.createRepository(IAuthRepository::class.java)?: throw IllegalStateException()
    }

}