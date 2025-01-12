package com.example.app_sudamericana.api.domain.dto

data class Permission(
    val dateCreated: String,
    val dateUpdated: String,
    val description: String,
    val idPermission: Int,
    val idRol: Int
)