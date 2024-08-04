package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "card_table")
data class CardEntity(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val number: String,
    val holdersName : String,
    val date : String,
    val cvv : Int
)
