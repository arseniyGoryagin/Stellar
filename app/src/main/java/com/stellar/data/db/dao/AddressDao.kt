package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.stellar.data.db.entetities.AddressEntity


@Dao
interface AddressDao {
    @Query("select * from address_table")
    suspend fun getAllAddresses() : List<AddressEntity>


    @Query("Update address_table set selected = 0 where selected = 1")
    suspend fun deselectAll()

    @Query ("Update address_table set selected = 1 where id = :id ")
    suspend fun selectNewAddress(id : Int)


    @Transaction
    suspend fun selectAddress(id  : Int){
        deselectAll()
        selectNewAddress(id)
    }
}