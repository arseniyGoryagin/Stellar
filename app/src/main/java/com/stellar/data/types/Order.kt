package com.stellar.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stellar.data.db.entetities.OrderEntity

data class Order(
    val id : Long = 0,
    val productID : Int,
    val status : Int,
    val qty: Int,
    val totalPrice : Long



){

    companion object{


        const val INPROGRESS = 0
        const val COMPLETED = 1


        fun toOrder(orderEntity : OrderEntity) : Order{
            return Order(
                id = orderEntity.id,
                productID = orderEntity.productID,
                status = orderEntity.status,
                qty = orderEntity.qty,
                totalPrice = orderEntity.totalPrice
            )
        }

    }


}