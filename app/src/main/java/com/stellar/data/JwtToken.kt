package com.stellar.data

import kotlinx.serialization.Serializable


@Serializable
data class JwtToken(
    var access_token  : String?,
    var refresh_token : String?
)