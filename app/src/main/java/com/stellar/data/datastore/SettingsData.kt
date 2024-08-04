package com.stellar.data.datastore


sealed interface Language{
    object English : Language
    object Spanish : Language
    object Chinese : Language
    object Russian : Language
}

data class Notifications(
    val payment : Boolean,
    val tracking : Boolean,
    val completeOrder : Boolean,
    val notification : Boolean
)

data class Security(
    val faceId : Boolean,
    val rememberPassword : Boolean,
    val touchID : Boolean,
)



data class SettingsData(
    val language: String,
    val notifications : Notifications,
    val security : Security
)