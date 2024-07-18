package com.stellar.data

import kotlinx.serialization.Serializable


@Serializable
data class JwtToken(
    val access_token  : String,
    val refresh_token : String
)