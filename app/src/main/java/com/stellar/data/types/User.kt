package com.stellar.data.types

import kotlinx.serialization.Serializable


@Serializable
class User (
    val id: Int,
    val email : String,
    val password: String,
    val name: String,
    val role: String,
    val creationAt: String,
    val updatedAt : String,
    val avatar: String,
)