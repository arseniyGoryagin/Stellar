package com.stellar.data.requests

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest (
    val email : String,
    val password : String
)