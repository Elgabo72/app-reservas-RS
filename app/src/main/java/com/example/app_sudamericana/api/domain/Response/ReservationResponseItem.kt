package com.example.app_sudamericana.api.domain.Response

data class ReservationResponseItem(
    val dateCreated: String,
    val dateUpdated: String,
    val description: String,
    val driver: Driver,
    val idDriver: Int,
    val idPassenger: Int,
    val idReservation: Int,
    val idStateReservation: Int,
    val idTariff: Int,
    val passenger: Passenger,
    val stateReservation: StateReservation,
    val tariff: Tariff,
    val travelDate: String
)