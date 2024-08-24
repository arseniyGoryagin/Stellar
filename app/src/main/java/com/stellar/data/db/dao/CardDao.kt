package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stellar.data.db.entetities.CardEntity


@Dao
interface CardDao {

    @Query("Select * from card_table")
    suspend fun getAllCards() : List<CardEntity>


    @Query("Select * from card_table where id = :id")
    suspend fun getCard( id : Int) : CardEntity


    @Insert
    suspend fun insertCard(card : CardEntity)

    @Query("Delete from card_table")
    suspend fun deleteAllCards()


}