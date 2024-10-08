package com.stellar.api

import com.stellar.data.types.Category
import com.stellar.data.requests.UserData
import com.stellar.data.types.JwtToken
import com.stellar.data.types.User
import com.stellar.data.dto.ProductDto
import com.stellar.data.requests.LoginRequest
import com.stellar.data.requests.NewPassword
import com.stellar.data.requests.RefreshToken
import com.stellar.data.requests.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface PlatziApi{

    @GET("products")
    suspend fun getNewArrivals(
        @Query("offset") offset : Int = 0,
        @Query("limit") limit : Int = 10,
    ): List<ProductDto>


    @GET("categories")
    suspend fun getAllCategories(
    ): List<Category>


    @GET("products")
    suspend fun getProducts(
        @Query("title") title : String,
        @Query("limit") limit : Int = 10,
        @Query("offset") offset: Int = 0,
        @Query("price_min") priceMin : Int? = null,
        @Query("price_max") priceMax: Int? = null,
        @Query("categoryId") categoryId: Int? = null
    ) : List<ProductDto>



    @GET("products/{id}")
    suspend fun getProduct(
        @Path("id") id: Int
    ): ProductDto


    @POST("users")
    suspend fun registerUser(
        @Body registerRequest: RegisterRequest
    ) : User


    @POST("auth/login")
    suspend fun auth(
        @Body loginRequest: LoginRequest
    ) : JwtToken


    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") token : String,
        @Body refreshToken: RefreshToken
    ) : JwtToken



    @GET("auth/profile")
    suspend fun getProfile(
        @Header("Authorization") token : String
    ) : User


    // Settings
    @PUT("users/{id}")
    suspend fun changePassword(
        @Path("id") id : Int,
        @Body newPassword : NewPassword
    )

    @PUT("users/{id}")
    suspend fun updateUserData(
        @Path("id") id : Int,
        @Body newData : UserData
    )
}