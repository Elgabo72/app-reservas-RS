package com.example.app_sudamericana.api.domain.dto

data class Role(
    val dateCreated: String,
    val dateUpdated: String,
    val description: String,
    val idRole: Int,
    val permissions: List<Permission>
)