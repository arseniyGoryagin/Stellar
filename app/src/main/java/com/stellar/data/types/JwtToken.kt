package com.stellar.data.types

import kotlinx.serialization.Serializable


@Serializable
data class JwtToken(
    var access_token  : String,
    var refresh_token : String
)