package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notifications_table")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val iconType : Int,
    val name : String,
    val description : String
)
