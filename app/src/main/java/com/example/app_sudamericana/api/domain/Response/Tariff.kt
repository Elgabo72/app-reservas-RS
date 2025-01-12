package com.example.app_sudamericana.api.domain.Response

data class Tariff(
    val active: Boolean,
    val amount: Int,
    val dateCreated: String,
    val dateUpdated: String,
    val description: String,
    val destination: String,
    val idTariff: Int,
    val origin: String
)