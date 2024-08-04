package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.stellar.data.db.entetities.OrderEntity


@Dao
interface OrderDao {
    @Query("Select * from orders_table")
    suspend fun getAllOrders() : List<OrderEntity>
    @Insert
    suspend fun addOrder( order : OrderEntity)


    @Query("Delete from orders_table")
    suspend fun clearAllOrders()
}