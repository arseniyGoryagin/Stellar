package com.stellar.data

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id : Int? = null,
    val name: String,
    val image: String? = null
){}
