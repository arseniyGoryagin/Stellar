package com.stellar.data

import kotlinx.serialization.Serializable


@Serializable
class User (
    val id: Int,
    val email : String,
    val password: String,
    val name: String,
    val role: String,
    val avatar: String,
)