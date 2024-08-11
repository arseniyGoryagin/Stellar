package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.stellar.data.db.entetities.AddressEntity


@Dao
interface AddressDao {
    @Query("select * from address_table")
    suspend fun getAllAddresses() : List<AddressEntity>

    @Query("select * from address_table where id = :id")
    suspend fun getAddress(id : Int) : AddressEntity
}