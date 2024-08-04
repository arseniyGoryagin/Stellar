package com.stellar.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.stellar.data.db.entetities.CartProductEntity
import com.stellar.data.db.entetities.FavoriteProductsEntity


@Dao
interface FavoriteProductsDao {

    @Query("Select * from favorite_products_table")
    suspend fun getFavoriteProducts() : List<FavoriteProductsEntity>

    @Query(" Delete from favorite_products_table where productID = :id")
    suspend fun removeFavoriteProduct(id : Int )

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavoriteProduct(product : FavoriteProductsEntity) : Long


    @Query("select Count(*) from favorite_products_table where productID = :productID")
    suspend fun isFavoriteProduct(productID : Int) : Int

    @Query("Delete from favorite_products_table")
    suspend fun clearAllFavoriteProducts()

}