package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stellar.data.db.entetities.Notification


@Dao
interface NotificationsDao {

    @Insert
    fun insertNotification(notification : Notification)

    @Query("Select * from notifications_table ORDER by id LIMIT :amount OFFSET :offset ")
    fun getNotifications(amount : Int, offset : Int) :  List<Notification>

}