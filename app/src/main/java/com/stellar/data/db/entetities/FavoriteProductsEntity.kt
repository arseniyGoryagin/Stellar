package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_products_table",
    indices = [Index(value = ["productID"], unique = true)])
data class FavoriteProductsEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val productID : Int
)
