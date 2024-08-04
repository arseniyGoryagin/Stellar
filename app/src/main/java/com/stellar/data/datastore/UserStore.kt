package com.stellar.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class UserStore(private val context : Context) {


    val TOKEN_KEY = stringPreferencesKey("token_key")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")


    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_data")

    }



    // Auth token

    suspend fun getTokenFlow() : Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[TOKEN_KEY] ?: ""
        }
    }

    fun getToken() : String =
        runBlocking {
            return@runBlocking getTokenFlow().first()
        }


    suspend fun saveToken(token  : String){
        context.dataStore.edit{ prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun removeToken(){
        context.dataStore.edit{ prefs ->
            prefs[TOKEN_KEY] = ""
        }
    }


    // Refresh Token

    fun getRefreshToken(): String = runBlocking {
        return@runBlocking getRefreshTokenFlow().first()
    }

    private fun getRefreshTokenFlow(): Flow<String> {
        return context.dataStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveRefreshToken(token  : String){
        context.dataStore.edit{ prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun removeRefreshToken(){
        context.dataStore.edit{ prefs ->
            prefs[TOKEN_KEY] = ""
        }
    }


}


