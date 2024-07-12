package com.stellar.data



data class UserEntity(
    var name : String,
    var username : String,
    var profilePhtot : String,
    var emai : String,
    var favorites : List<Int> = emptyList()
) {



}