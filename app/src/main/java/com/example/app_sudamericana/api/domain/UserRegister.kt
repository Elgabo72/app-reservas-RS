package com.example.app_sudamericana.api.domain
import com.google.gson.annotations.SerializedName

    data class UserRegister(
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("idRol")
    val idRol: Int,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("username")
    val username: String
)
