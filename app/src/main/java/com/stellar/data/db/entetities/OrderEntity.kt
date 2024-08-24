package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stellar.data.types.Order

@Entity(tableName = "orders_table")
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val productID : Int,
    val status : Int = Order.INPROGRESS,
    val totalPrice : Long,
    val qty : Int
)