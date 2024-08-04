package com.stellar.data.db.dao

import androidx.compose.ui.geometry.Offset
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.stellar.data.datastore.Notifications
import com.stellar.data.db.entetities.NotificationEntity


@Dao
interface NotificationsDao {

    @Insert
    suspend fun insertNotification(notification : NotificationEntity)

    @Query("Select * from notifications_table order by id LIMIT :limit OFFSET :offset ")
    suspend fun getNotifications(offset: Int, limit : Int ) : List<NotificationEntity>


    @Query("Delete from notifications_table")
    suspend fun clearAllNotifications()


}