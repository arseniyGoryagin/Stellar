package com.stellar.data.types

import com.stellar.data.db.entetities.CardEntity
import java.util.Date

data class Card(
    val id : Int,
    val number: String,
    val holdersName : String,
    val date : String,
    val cvv : Int
){


    companion object{
        fun toCard(cardEntity: CardEntity) : Card{
            return Card(
                holdersName = cardEntity.holdersName,
                number = cardEntity.number,
                date = cardEntity.date,
                cvv = cardEntity.cvv,
                id = cardEntity.id
                )
        }
    }



}
