package com.example.app_sudamericana.api.domain.Response
import com.google.gson.annotations.SerializedName


data class UserRegisterResponse(
    @SerializedName("active")
    val active: Boolean,
    @SerializedName("address")
    val address: String,
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("dateUpdated")
    val dateUpdated: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("firstName")
    val firstName: String,
    @SerializedName("idRol")
    val idRol: Int,
    @SerializedName("idUser")
    val idUser: Int,
    @SerializedName("lastName")
    val lastName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("role")
    val role: Role,
    @SerializedName("username")
    val username: String,
    @SerializedName("vehicles")
    val vehicles: List<Vehicle>
) {
    data class Role(
        @SerializedName("dateCreated")
        val dateCreated: String,
        @SerializedName("dateUpdated")
        val dateUpdated: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("idRole")
        val idRole: Int,
        @SerializedName("permissions")
        val permissions: List<Permission>
    ) {
        data class Permission(
            @SerializedName("dateCreated")
            val dateCreated: String,
            @SerializedName("dateUpdated")
            val dateUpdated: String,
            @SerializedName("description")
            val description: String,
            @SerializedName("idPermission")
            val idPermission: Int,
            @SerializedName("idRol")
            val idRol: Int
        )
    }

    data class Vehicle(
        @SerializedName("dateCreated")
        val dateCreated: String,
        @SerializedName("dateUpdated")
        val dateUpdated: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("idUser")
        val idUser: Int,
        @SerializedName("idVehicle")
        val idVehicle: Int,
        @SerializedName("mark")
        val mark: String,
        @SerializedName("model")
        val model: String,
        @SerializedName("plaque")
        val plaque: String,
        @SerializedName("type")
        val type: String
    )
}