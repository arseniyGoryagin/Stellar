package com.stellar.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.stellar.data.db.entetities.ProductEntity
import javax.inject.Inject


@Dao
interface ProductDao {

    /*

    @Insert
    suspend fun upsertProducts(products : List<ProductEntity>)


    @Query("Select * from product_table")
    suspend fun pagingSource() : PagingSource<Int, ProductEntity>


    @Query("Delete from product_table")
    suspend fun clearAll() : Int*/

}