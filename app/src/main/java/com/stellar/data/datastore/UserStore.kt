package com.stellar.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.stellar.data.types.Address
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import com.stellar.data.types.Card

class UserStore(private val context : Context) {


    val TOKEN_KEY = stringPreferencesKey("token_key")
    val REFRESH_TOKEN_KEY = stringPreferencesKey("refresh_token")
    val LATESTSEARCHES = stringSetPreferencesKey("latest_searches")



    companion object{
        private val Context.tokenStore: DataStore<Preferences> by preferencesDataStore("user_data")
        private val Context.latestSearches : DataStore<Preferences> by preferencesDataStore("latest_searches")
        private val Context.addressStore: DataStore<AddressProto> by dataStore(
            fileName = "address.proto",
            serializer = AddressSerializer)
        private val Context.cardStore: DataStore<CardProto> by dataStore(
            fileName = "card.proto",
            serializer = CardSerializer)
    }



    // Address
    suspend fun selectAddress(addr : Address){

        context.addressStore.updateData { prefs ->
            prefs.toBuilder().setId(addr.id).setTitle(addr.title).setFullTitle(addr.fullTitle).build()
        }
    }


    suspend fun getSelectedAddress() : Flow<AddressProto>{
       return context.addressStore.data
    }




    // Card
    suspend fun selectCard(card : Card){
        context.cardStore.updateData { prefs ->
            prefs.toBuilder().setId(card.id)
                .setCvv(card.cvv)
                .setDate(card.date)
                .setNumber(card.number)
                .setHoldersName(card.holdersName)
                .build()
        }
    }
    suspend fun getSelectedCard() : Flow<CardProto>{
        return context.cardStore.data
    }


    // Searches
    suspend fun getLatestSearches() : Flow<List<String>> {
       return context.latestSearches.data.map { prefs ->
           prefs[LATESTSEARCHES]?.toList() ?: emptyList()
       }
    }

    suspend fun removeSearch(search : String)
    {
        context.latestSearches.edit { prefs ->
            val currentSearches = prefs[LATESTSEARCHES] ?: emptySet()
            val newSearches = currentSearches.toMutableSet().apply {
                remove(search)
            }
            prefs[LATESTSEARCHES] = newSearches
        }
    }

    suspend fun removeAllSearches() {
        context.latestSearches.edit { prefs ->
            prefs.clear()
        }
    }

    suspend fun addSearch(search : String)  {
        context.latestSearches.edit { prefs ->
            val currentSearches = prefs[LATESTSEARCHES] ?: emptySet()
            val newSearches = currentSearches.toMutableSet().apply {
                add(search)
            }
            prefs[LATESTSEARCHES] = newSearches
        }
    }



    // Auth token
    suspend fun getTokenFlow() : Flow<String> {
        return context.tokenStore.data.map { prefs ->
            prefs[TOKEN_KEY] ?: ""
        }
    }

    fun getToken() : String =
        runBlocking {
            return@runBlocking getTokenFlow().first()
        }


    suspend fun saveToken(token  : String){
        context.tokenStore.edit{ prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun removeToken(){
        context.tokenStore.edit{ prefs ->
            prefs[TOKEN_KEY] = ""
        }
    }


    // Refresh Token

    fun getRefreshToken(): String = runBlocking {
        return@runBlocking getRefreshTokenFlow().first()
    }

    private fun getRefreshTokenFlow(): Flow<String> {
        return context.tokenStore.data.map { prefs ->
            prefs[REFRESH_TOKEN_KEY] ?: ""
        }
    }

    suspend fun saveRefreshToken(token  : String){
        context.tokenStore.edit{ prefs ->
            prefs[REFRESH_TOKEN_KEY] = token
        }
    }

    suspend fun removeRefreshToken(){
        context.tokenStore.edit{ prefs ->
            prefs[TOKEN_KEY] = ""
        }
    }



}


