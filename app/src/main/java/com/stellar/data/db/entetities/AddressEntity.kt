package com.stellar.data.db.entetities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "address_table")
data class AddressEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val title : String,
    val fullTitle : String,
    val selected : Boolean
)