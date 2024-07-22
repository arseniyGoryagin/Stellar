package com.stellar.data.requests

import kotlinx.serialization.Serializable


@Serializable
data class NewPassword(
    val newPassword: String
)