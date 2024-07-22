package com.stellar.data

import android.content.Context
import androidx.core.content.contentValuesOf
import androidx.lifecycle.MutableLiveData
import com.stellar.api.PlatziApi
import com.stellar.data.db.Db
import com.stellar.data.db.dao.NotificationsDao
import com.stellar.data.db.entetities.Notification
import com.stellar.data.requests.LoginRequest
import com.stellar.data.requests.NewPassword
import com.stellar.data.requests.RefreshToken
import com.stellar.data.requests.RegisterRequest
import com.stellar.data.requests.UserData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject








class Repository @Inject constructor(private val platziApi: PlatziApi, private val db : Db, @ApplicationContext private val  context: Context){

    var userData  = MutableLiveData<UserEntity>(UserEntity("Wiliam", "Buyer33", "-", "hello@gmai.com"))

    private var favoriteItems = mutableListOf<Int>()
    private var productsInCart = mutableListOf<Int>()
    private var myOrders = mutableListOf<Int>()
    private var latestSearches = mutableListOf<String>()

    lateinit var jwtToken : JwtToken


    val notificationsDao = db.notificationDao()




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


    private suspend fun getUser(jwtToken: JwtToken) : User{
        return platziApi.getProfile("Bearer ${jwtToken.access_token!!}")
    }


    suspend fun  getUserData() : User{
            return getUser(jwtToken)
    }






    // JWT

    private suspend fun auth(email: String, password: String) : JwtToken{
        return platziApi.auth(LoginRequest(email, password))
    }

    suspend fun login(email: String, password: String) : User{
        println(email +  password)
        val jwtTokenVal = auth(email, password)
        println("HUHUH + " + jwtToken)
        jwtToken = jwtTokenVal
        saveJwtTokenToPrefs(jwtTokenVal)
        return getUser(jwtToken)
    }

    private fun saveJwtTokenToPrefs(jwtTokenVal : JwtToken) {

        println("SAVE JWT_TOOOOOKEN = \n" + jwtToken)
        println("SAVE REFRESH TOKEN  = \n" + jwtToken)

        val prefs = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("JWT_TOKEN", jwtTokenVal.access_token)
            .putString("REFRESH_TOKEN", jwtTokenVal.refresh_token)
            .apply()
    }

    suspend fun registerUser(email : String, password : String, name : String) : User{
        val user = platziApi.registerUser(RegisterRequest(email, password, name, avatar ="https://imgur.com/gallery/im-london-doing-best-to-keep-low-profile-DpmCVGl", role = "customer"))
        val jwtTokenVal = auth(email, password)
        jwtToken = jwtTokenVal
        saveJwtTokenToPrefs(jwtToken)
        return user
    }

    private fun getJwtTokenFromPrefs() : JwtToken{
        val prefs = context.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        val jwtToken = prefs.getString("JWT_TOKEN", null)
        val refreshToken = prefs.getString("REFRESH_TOKEN", null)
        val jwtTokenVal = JwtToken(jwtToken, refreshToken)
        println("JWT_TOOOOOKEN = \n" + jwtToken)
        println("REFRESH TOKEN  = \n" + jwtToken)
        return jwtTokenVal
    }

    fun getJwt() : JwtToken{
        return jwtToken
    }

    fun clearToken(){
        jwtToken.access_token = null
        jwtToken.refresh_token = null
    }

    suspend fun refreshToken(){
        if(jwtToken.refresh_token != null){
            val refreshToken = jwtToken.refresh_token!!
            val jwtTokenVal = platziApi.refreshToken( "Bearer ${jwtToken.access_token!!}", RefreshToken(refreshToken))
            jwtToken = jwtTokenVal
        }
    }


    // Settings

    suspend fun changePassword(newPassword: String) {
        println("JWTTTT TOKEN _____ \n" + jwtToken)
        val user = getUser(jwtToken)
        val userId = user.id
        println("Id + " + userId)
        return platziApi.changePassword( userId, NewPassword(newPassword))
    }

    suspend fun updateUserData(name : String, email : String) {
        val user = getUser(jwtToken)
        val userId = user.id
        println("Email == " + email)
        return platziApi.updateUserData(userId, UserData(name, email))
    }


    // Notifications

    suspend fun addNotification(notification: Notification){
        notificationsDao.insertNotification(notification)
    }
    suspend fun getNotification(amount : Int, offset : Int) : List<Notification>{
        return notificationsDao.getNotifications(amount, offset)
    }

    init {
        jwtToken = getJwtTokenFromPrefs()
    }

}