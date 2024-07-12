package com.stellar.data

import androidx.lifecycle.MutableLiveData
import com.stellar.api.PlatziApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject








class Repository @Inject constructor(private val platziApi: PlatziApi){

    var userData  = MutableLiveData<UserEntity>(UserEntity("Wiliam", "Buyer33", "-", "hello@gmai.com"))

    private var favoriteItems = mutableListOf<Int>()
    private var myOrders = mutableListOf<Int>()




    suspend fun getNewArrivals() : List<Product>{
        return platziApi.getNewArrivals()
    }
    suspend fun getCategories(): List<Category> {
        return platziApi.getAllCategories()
    }
    suspend fun getProducts(title: String, limit: Int, filter: SearchFilter): List<Product>{
        return platziApi.getProducts(title, limit, filter.price_min.toInt(), filter.price_max.toInt(), filter.categoryId)
    }
    suspend fun getFavoriteProducts(){

    }
    suspend fun addFavorite(id : Int){
        favoriteItems.add(id)
    }







    var latestSearches = MutableStateFlow<List<String>>(emptyList())

    fun addLastSearch(){

        if(latestSearches.value.size > 4) {

        }else{
            //latestSearches.value.add()

        }

    }

    fun removeLastSearch(){


    }


    fun updateUserData() {
        TODO("Not yet implemented")
    }


    /*
    // Searches
    fun getLatestSearches(): List<String> {
        //return latestSearches
    }

    fun addLatestSearch(search : String){
        //latestSearches.add(search)
    }*/








}