package com.stellar.data.types

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id : Int? = null,
    val name: String,
    val image: String? = null,
    val creationAt : String,
    val updatedAt : String,
)
