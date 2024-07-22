package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notifications_table")
data class Notification(

    @PrimaryKey(autoGenerate = true)
    val id : Int,

    val icon : Int,
    val name : String,
    val description : String
)
