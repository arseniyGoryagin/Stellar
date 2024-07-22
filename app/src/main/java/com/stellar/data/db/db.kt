package com.stellar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stellar.data.db.dao.NotificationsDao
import com.stellar.data.db.entetities.Notification


@Database(entities = [Notification::class], version = 1)
abstract class Db  : RoomDatabase(){


    abstract fun notificationDao() : NotificationsDao

    companion object {



            @Volatile
            private var INSTANCE : Db ? = null
            fun getDatabase(context : Context) : Db{
                return INSTANCE ?: synchronized(this){

                    val instance  = Room.databaseBuilder(

                        context.applicationContext,
                        Db::class.java,
                        "Db",


                    ).build()
                    INSTANCE = instance
                    instance
                }
            }


    }
}