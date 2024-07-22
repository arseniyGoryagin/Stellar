package com.stellar.data.requests

import kotlinx.serialization.Serializable


@Serializable
data class RefreshToken (
    val refreshToken: String
)