package com.stellar.data.requests

import kotlinx.serialization.Serializable


@Serializable
data class UserData(
    val name : String,
    val email : String
)
