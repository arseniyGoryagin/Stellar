package com.stellar.data.requests

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest (
    val email : String,
    val password : String,
    val name : String,
    val avatar : String,
    val role : String,
)