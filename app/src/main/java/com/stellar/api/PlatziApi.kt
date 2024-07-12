package com.stellar.api

import com.stellar.data.Category
import com.stellar.data.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface PlatziApi{

    @GET("products")
    suspend fun getNewArrivals(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = 10,
    ): List<Product>


    @GET("categories")
    suspend fun getAllCategories(
    ): List<Category>


    @GET("products")
    suspend fun getProducts(
        @Query("title") title : String,
        @Query("limit") limit : Int = 10,
        @Query("price_min") priceMin : Int = 0,
        @Query("price_max") priceMax: Int = Int.MAX_VALUE,
        @Query("Category") categoryId: Int? = null
    ) : List<Product>


}