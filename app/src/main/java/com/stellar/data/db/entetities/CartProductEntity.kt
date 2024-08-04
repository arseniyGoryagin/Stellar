package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "cart_products",
        indices = [Index(value = ["productID"], unique = true)])
data class CartProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val productID : Int,
    val qty : Int = 1
){
}