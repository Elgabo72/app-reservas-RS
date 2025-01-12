package com.example.app_sudamericana.api.domain.dto

data class UserUpdateDto(
    val active: Boolean,
    val address: String,
   // val dateCreated: String,
    //val dateUpdated: String,
    val email: String,
    val firstName: String,
   // val idRol: Int,
    val idUser: Int,
    val lastName: String,
    val password: String,
    val phone: String,
   // val role: Role,
    val username: String


)