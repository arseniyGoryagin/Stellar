package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stellar.data.db.entetities.CardEntity


@Dao
interface CardDao {

    @Query("Select * from card_table")
    suspend fun getAllCards() : List<CardEntity>


    @Insert
    suspend fun insertCard(card : CardEntity)

}