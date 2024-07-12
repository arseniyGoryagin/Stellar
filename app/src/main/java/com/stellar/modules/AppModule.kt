package com.stellar.modules

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.stellar.api.PlatziApi
import com.stellar.data.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton





@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRepo(api : PlatziApi) : Repository {
        return Repository(api)
    }


    @Provides
    @Singleton
    fun proivdeRetroFit() : PlatziApi{

        val BASE_URL = "https://api.escuelajs.co/api/v1/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json{ignoreUnknownKeys = true}.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PlatziApi::class.java)
    }




}