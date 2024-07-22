package com.stellar.modules

import android.content.Context
import android.content.SharedPreferences
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.stellar.api.PlatziApi
import com.stellar.data.Repository
import com.stellar.data.db.Db
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.contracts.contract


@Module
@InstallIn(SingletonComponent::class)
class AppModule {



    @Provides
    @Singleton
    fun provideRepo(api : PlatziApi, @ApplicationContext context: Context, db : Db) : Repository {
        return Repository(api, context= context,  db = db)
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


    @Provides
    @Singleton
    fun provideSharedPrefs (@ApplicationContext context : Context) : SharedPreferences{
        val PREFSTAG = "PREFS"
        return context.getSharedPreferences( PREFSTAG, Context.MODE_PRIVATE)
    }


    @Provides
    @Singleton
    fun provideDb(@ApplicationContext context : Context) : Db {
        return Db.getDatabase(context)
    }


}