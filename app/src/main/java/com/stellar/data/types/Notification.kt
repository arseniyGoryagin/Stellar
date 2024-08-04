package com.stellar.data.types

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.stellar.data.db.entetities.NotificationEntity


data class Notification(
    val id : Int,
    val iconType : Int,
    val name : String,
    val description : String
){

    companion object{

        const val PURCHASE_ICON = 0
        const val SALE_ICON = 1
        const val SENT_ICON  = 2

        fun toNotificaton(notifEntity: NotificationEntity) : Notification{
            return Notification(
                id = notifEntity.id,
                iconType = notifEntity.iconType,
                name = notifEntity.name,
                description = notifEntity.description
            )
        }
    }



}