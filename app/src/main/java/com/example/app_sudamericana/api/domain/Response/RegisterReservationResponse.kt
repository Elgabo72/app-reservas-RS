package com.example.app_sudamericana.api.domain.Response


import com.google.gson.annotations.SerializedName

data class RegisterReservationResponse(
    @SerializedName("dateCreated")
    val dateCreated: String,
    @SerializedName("dateUpdated")
    val dateUpdated: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("driver")
    val driver: Driver,
    @SerializedName("idDriver")
    val idDriver: Int,
    @SerializedName("idPassenger")
    val idPassenger: Int,
    @SerializedName("idReservation")
    val idReservation: Int,
    @SerializedName("idStateReservation")
    val idStateReservation: Int,
    @SerializedName("idTariff")
    val idTariff: Int,
    @SerializedName("passenger")
    val passenger: Passenger,
    @SerializedName("stateReservation")
    val stateReservation: StateReservation,
    @SerializedName("tariff")
    val tariff: Tariff,
    @SerializedName("travelDate")
    val travelDate: String
) {
    data class Driver(
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

    data class Passenger(
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

    data class StateReservation(
        @SerializedName("dateCreated")
        val dateCreated: String,
        @SerializedName("dateUpdated")
        val dateUpdated: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("idStateReservation")
        val idStateReservation: Int
    )

    data class Tariff(
        @SerializedName("amount")
        val amount: Int,
        @SerializedName("dateCreated")
        val dateCreated: String,
        @SerializedName("dateUpdated")
        val dateUpdated: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("destination")
        val destination: String,
        @SerializedName("idTariff")
        val idTariff: Int,
        @SerializedName("origin")
        val origin: String
    )
}