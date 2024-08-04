package com.stellar.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.stellar.data.db.dao.AddressDao
import com.stellar.data.db.dao.CardDao
import com.stellar.data.db.dao.CartProductsDao
import com.stellar.data.db.dao.FavoriteProductsDao
import com.stellar.data.db.dao.NotificationsDao
import com.stellar.data.db.dao.OrderDao
import com.stellar.data.db.dao.ProductDao
import com.stellar.data.db.entetities.AddressEntity
import com.stellar.data.db.entetities.CardEntity
import com.stellar.data.db.entetities.CartProductEntity
import com.stellar.data.db.entetities.FavoriteProductsEntity
import com.stellar.data.db.entetities.NotificationEntity
import com.stellar.data.db.entetities.OrderEntity
import com.stellar.data.db.entetities.ProductEntity


@Database(entities = [
    NotificationEntity::class,
    ProductEntity::class,
    AddressEntity::class,
    CardEntity::class,
    CartProductEntity::class,
    OrderEntity::class,
    FavoriteProductsEntity::class], version = 1)
abstract class Db  : RoomDatabase(){


    abstract fun notificationDao() : NotificationsDao
    abstract fun productDao () : ProductDao
    abstract fun addressDao() : AddressDao
    abstract fun cardDao() : CardDao
    abstract fun cartProductsDao() : CartProductsDao
    abstract fun ordersDao() : OrderDao
    abstract fun favoriteProductsDao() :FavoriteProductsDao

    companion object {



            @Volatile
            private var INSTANCE : Db ? = null
            fun getDatabase(context : Context) : Db{
                return INSTANCE ?: synchronized(this){

                    val instance  = Room.databaseBuilder(

                        context.applicationContext,
                        Db::class.java,
                        "Db",


                    ).createFromAsset("stellar").build()
                    INSTANCE = instance
                    instance
                }
            }


    }
}