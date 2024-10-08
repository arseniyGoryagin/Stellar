package com.stellar.data.db.dao

import android.database.sqlite.SQLiteConstraintException
import androidx.paging.LoadState
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.stellar.data.db.entetities.CartProductEntity
import com.stellar.data.types.CartProduct

@Dao
interface CartProductsDao {


    @Query("Select * from cart_products")
    suspend fun getAllCartProducts() : List<CartProductEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun tryInsertCartProduct(product : CartProductEntity) : Long


    @Transaction
    suspend fun insertOrAddQtyCartProduct(product : CartProductEntity){
        val id = tryInsertCartProduct(product)
        println("IDIDIDIDID D+++++++ " + id)
        if (id == -1L) {
            addByProductId(product.productID)
        }
    }


    @Query("Update cart_products set qty = qty + 1 where id = :id")
    suspend fun addQty(id : Long)

    @Query("Update cart_products set qty = qty + 1 where productID = :id")
    suspend fun addByProductId(id : Int)

    @Query("Update cart_products set qty = qty -1 where id = :id")
    suspend fun removeQty(id : Long)



    @Query("Delete from cart_products where id = :id")
    suspend fun removeCartProduct(id : Long)

    @Query("Delete from cart_products")
    suspend fun clearCart()




}