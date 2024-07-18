package com.stellar.data

import androidx.lifecycle.MutableLiveData
import com.stellar.api.PlatziApi
import com.stellar.data.requests.LoginRequest
import com.stellar.data.requests.RegisterRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject








class Repository @Inject constructor(private val platziApi: PlatziApi){

    var userData  = MutableLiveData<UserEntity>(UserEntity("Wiliam", "Buyer33", "-", "hello@gmai.com"))

    private var favoriteItems = mutableListOf<Int>()
    private var productsInCart = mutableListOf<Int>()
    private var myOrders = mutableListOf<Int>()
    private var latestSearches = mutableListOf<String>()

    private lateinit var jwtToken : JwtToken



    suspend fun getNewArrivals() : List<Product>{
        return toProducts(platziApi.getNewArrivals())
    }
    suspend fun getCategories(): List<Category> {
        return platziApi.getAllCategories()
    }
    suspend fun getProducts(title: String, limit: Int, filter: SearchFilter): List<Product>{
        return toProducts(platziApi.getProducts(title, limit, filter.price_min.toInt(), filter.price_max.toInt(), filter.categoryId))
    }

    suspend fun getProduct(id: Int): Product {
        println("Product req " + id)
        val product = platziApi.getProduct(id)
        println("returnes products \n" + product)
        return toProduct(product)

    }



    // Cart
    suspend fun getCartProducts(): List<Product> {
        return productsInCart.map {
            withContext(Dispatchers.IO){
                getProduct(it)
            }
        }
    }
    fun addToCart(id : Int){
        productsInCart.add(id)
    }
    fun removeFromCart(id : Int){
        productsInCart.remove(id)
    }


    // Favorite
    fun addFavorite(id : Int){
        if(id in favoriteItems)
            return
        favoriteItems.add(id)
    }
    fun removeFavorite(id : Int){
        favoriteItems.remove(id)
    }
    suspend fun getFavoriteProducts(): List<Product> {
        return favoriteItems.map { itemId ->
            withContext(Dispatchers.IO) {
                getProduct(itemId)
            }
        }
    }


    // Searhces

    // TODO fix here


    private fun toProducts(products : List<BaseProduct>) : List<Product>{
       return  products.map {
           toProduct(it)
        }

    }

    private fun toProduct(product : BaseProduct) : Product{
        val isFavorite = product.id in favoriteItems
        return  BaseProduct.BaseProductToProduct(product,isFavorite)
    }

    suspend fun getPopularSearches() : List<Product>{
        val products = platziApi.getProducts("", limit = 1)
        println(products)
        return toProducts(products.take(4))
    }

    fun addSearch(search: String) {
        latestSearches.add(search)
    }

    fun removeSearch(search: String) {
        latestSearches.remove(search)
    }

    fun getLatestSearches() : List<String>{
        println("Latest searches = \n" + latestSearches)
        return latestSearches
    }


    // User

    private suspend fun auth(email: String, password: String) : JwtToken{
        return platziApi.auth(LoginRequest(email, password))
    }

    private suspend fun getUser(jwtToken: JwtToken) : User{
        return platziApi.getProfile(jwtToken.access_token)
    }


    suspend fun getUserData() : User{
        return getUser(jwtToken)
    }
    suspend fun login(email: String, password: String) : User{
        jwtToken = auth(email, password)
        return getUser(jwtToken)
    }

    suspend fun registerUser(email : String, password : String, avatar : String, name : String) : User{
            val user = platziApi.registerUser(RegisterRequest(email, password, name, avatar ="https://imgur.com/gallery/im-london-doing-best-to-keep-low-profile-DpmCVGl", role = "customer"))
            jwtToken = auth(email, password)
            return user
    }




}