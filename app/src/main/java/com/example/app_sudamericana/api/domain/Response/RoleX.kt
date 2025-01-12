package com.example.app_sudamericana.api.domain.Response

data class RoleX(
    val dateCreated: String,
    val dateUpdated: String,
    val description: String,
    val idRole: Int,
    val permissions: List<PermissionX>
)