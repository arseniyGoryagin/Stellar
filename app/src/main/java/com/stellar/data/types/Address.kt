package com.stellar.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stellar.data.db.entetities.AddressEntity

data class Address (
    val id : Int,
    val title : String,
    val fullTitle : String,
    val selected : Boolean
){
    companion object{


        fun entityToType(addr : AddressEntity) : Address{
            return Address(
                id = addr.id,
                title = addr.title,
                fullTitle = addr.fullTitle,
                selected = addr.selected
            )
        }


    }
}