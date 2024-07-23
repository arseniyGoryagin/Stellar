package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "product_table")
data class ProductEntity(

    @PrimaryKey
    val id : Int,

    val title : String,
    val price : Long,
    val description : String,
    val category: String,
    val images : String,
    var favorite : Boolean

    //val creationAt : String,
    //val updatedAt : String,
)