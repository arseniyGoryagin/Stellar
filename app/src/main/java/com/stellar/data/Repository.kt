package com.stellar.data

import android.content.Context
import androidx.datastore.dataStore
import androidx.lifecycle.MutableLiveData
import com.stellar.api.PlatziApi
import com.stellar.data.datastore.UserStore
import com.stellar.data.db.Db
import com.stellar.data.db.entetities.CardEntity
import com.stellar.data.db.entetities.CartProductEntity
import com.stellar.data.db.entetities.FavoriteProductsEntity
import com.stellar.data.db.entetities.NotificationEntity
import com.stellar.data.db.entetities.OrderEntity
import com.stellar.data.db.entetities.ProductEntity
import com.stellar.data.requests.LoginRequest
import com.stellar.data.requests.NewPassword
import com.stellar.data.requests.RefreshToken
import com.stellar.data.requests.RegisterRequest
import com.stellar.data.requests.UserData
import com.stellar.data.types.Address
import com.stellar.data.types.Card
import com.stellar.data.types.CartProduct
import com.stellar.data.types.CartProductWithProduct
import com.stellar.data.types.FavoriteProductWithProduct
import com.stellar.data.types.Order
import com.stellar.data.types.OrderWithProduct
import com.stellar.data.types.Product
import com.stellar.viewmodels.SearchFilter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject








class Repository @Inject constructor(
    private val userDataStore : UserStore,
    private val platziApi: PlatziApi,
    private val db : Db,
    @ApplicationContext private val  context: Context){

    var userData  = MutableLiveData<UserEntity>(UserEntity("Wiliam", "Buyer33", "-", "hello@gmai.com"))


    private var latestSearches = mutableListOf<String>()


    private var token = userDataStore.getToken()
    private var refreshToken = userDataStore.getRefreshToken()


    private val notificationsDao = db.notificationDao()
    private  val productDao = db.productDao()
    private val addressDao = db.addressDao()
    private val cardDao = db.cardDao()
    private val cartProductsDao = db.cartProductsDao()
    private val ordersDao = db.ordersDao()
    private val favoriteProductsDao = db.favoriteProductsDao()



    suspend fun getNewArrivals() : List<FavoriteProductWithProduct>{
        return  platziApi.getNewArrivals().map { productDto ->
            val product = Product.toProduct(productDto)
            FavoriteProductWithProduct(product = product, favorite = favoriteProductsDao.isFavoriteProduct(product.id) == 1)
        }
    }
    suspend fun getCategories(): List<Category> {
        return platziApi.getAllCategories()
    }

    suspend fun getProducts(title: String, limit: Int, filter: SearchFilter): List<FavoriteProductWithProduct>{
        val priceRange = filter.priceRange
        return platziApi.getProducts(title, limit, priceRange.start.toInt(), priceRange.endInclusive.toInt(), filter.categoryId).map {
            val product = Product.toProduct(it)
            FavoriteProductWithProduct(product = product, favorite = favoriteProductsDao.isFavoriteProduct(product.id) == 1)
        }
    }

    suspend fun getProduct(id: Int): Product {
        return Product.toProduct(platziApi.getProduct(id))
    }


    // Products\
    suspend fun getSearchProducts(searchString : String, page: Int, perPage: Int): List<FavoriteProductWithProduct> {
        return withContext(Dispatchers.IO) {
            platziApi.getProducts(
                title = searchString,
                limit = perPage,
                offset = page * perPage).map {
                Product.toProduct(it)
            }.map {
                FavoriteProductWithProduct(
                    product = it,
                    favorite = favoriteProductsDao.isFavoriteProduct(it.id) == 1
                )
            }
        }

    }

    suspend fun updateDbWithProducts(products : List<ProductEntity>){
        //productDao.upsertProducts(products)
    }

    suspend fun clearAllProducts(){
       // productDao.clearAll()
    }



    // Cart
    suspend fun getCartProducts(): List<CartProductWithProduct> {
        return  cartProductsDao.getAllCartProducts().map {
             CartProduct.toCartProduct(it)
        }.map {
            withContext(Dispatchers.IO){
                val product = getProduct(it.productID)
                CartProductWithProduct(cartProduct = it, product = product)
            }

        }
    }

    suspend fun addToCart(id : Int){
        cartProductsDao.insertOrAddQtyCartProduct(CartProductEntity(productID = id))
    }
    suspend fun removeFromCart(id : Long){
        cartProductsDao.removeCartProduct(id)
    }
    suspend fun addItemQty(id: Long) {
        cartProductsDao.addQty(id)
    }
    suspend fun removeCartProductQty(id: Long) {
        cartProductsDao.removeCartProduct(id)
    }
    suspend fun clearCart(){
        return cartProductsDao.clearCart()
    }






    // Favorite


    suspend fun addFavorite(id : Int){
        favoriteProductsDao.insertFavoriteProduct(FavoriteProductsEntity(productID = id))
    }
    suspend fun removeFavorite(id : Int){
        favoriteProductsDao.removeFavoriteProduct(id)
    }

    suspend fun getFavoriteProducts(): List<FavoriteProductWithProduct> {
        return favoriteProductsDao.getFavoriteProducts().map { favoriteProduct ->
            withContext(Dispatchers.IO) {
                val product = getProduct(favoriteProduct.productID)
                FavoriteProductWithProduct(
                    product = product,
                    favorite = favoriteProductsDao.isFavoriteProduct(product.id) == 1
                )
            }

        }
    }






    // Searhces
    suspend fun getPopularSearches() : List<PopularProduct>{
        val products = platziApi.getProducts("", limit = 1)
        println(products)
        return toPopularProducts((products.take(4)).map {
            Product.toProduct(it)
        })
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

    /*
    // Utility to
    private fun  toProducts(products : List<ProductDto>) : List<Product>{
        return  products.map {
            toProduct(it)
        }
    }
    private fun  toProductEnteties(products : List<ProductDto>) : List<ProductEntity>{
        return  products.map {
            toProductEntity(it)
        }
    }
    private fun toProduct(product : ProductDto) : Product {
        val isFavorite = product.id in favoriteItems
        return  ProductDto.ProductDtoToProduct(product,isFavorite)
    }
    private fun toProductEntity(product : ProductDto) : ProductEntity{
        val isFavorite = product.id in favoriteItems
        return  ProductDto.ProductDtoToProductEntity(product,isFavorite)
    }*/



    private fun toPopularProduct(product : Product) : PopularProduct{
        return PopularProduct(product, type = randomType(), searches = randomSearches() )
    }

    private fun toPopularProducts(products : List<Product>) : List<PopularProduct> {
        return products.map {
            toPopularProduct(it)
        }
    }
    private fun randomType() : String{
        val type = listOf("Hot", "New", "Popular")
        val number = (0..type.size-1).random()
        return type[number]
    }

    private fun randomSearches() : String{
       return (1000..5000).random().toString()
    }






    // JWT User


    private suspend fun getUser(token : String) : User{
        return platziApi.getProfile("Bearer ${token}")
    }


    suspend fun  getUserData() : User{
           return getUser(token)
    }


    private suspend fun auth(email: String, password: String) : JwtToken{
        return platziApi.auth(LoginRequest(email, password))
    }

    suspend fun login(email: String, password: String) : User{
        val jwtTokenVal = auth(email, password)
        token = jwtTokenVal.access_token
        refreshToken = jwtTokenVal.refresh_token
        return getUser(token)
    }

    suspend fun registerUserAndAuth(email : String, password : String, name : String) : User{
        val user = platziApi.registerUser(RegisterRequest(email, password, name, avatar ="https://imgur.com/gallery/im-london-doing-best-to-keep-low-profile-DpmCVGl", role = "customer"))
        val jwtTokenVal = auth(email, password)
        token = jwtTokenVal.access_token
        refreshToken = jwtTokenVal.refresh_token
        return user
    }


    suspend fun clearToken(){
        userDataStore.removeToken()
        userDataStore.removeRefreshToken()
    }

    suspend fun refreshToken(){
        val jwtTokenVal = platziApi.refreshToken( "Bearer ${token}", RefreshToken(refreshToken))
        token = jwtTokenVal.access_token
        refreshToken = jwtTokenVal.refresh_token
    }

    suspend fun saveToken(){
        userDataStore.saveToken(token)
        userDataStore.saveRefreshToken(refreshToken)
    }



    // Settings

    suspend fun changePassword(newPassword: String) {
        val user = getUser(token)
        val userId = user.id
        println("Id + " + userId)
        return platziApi.changePassword( userId, NewPassword(newPassword))
    }

    suspend fun updateUserData(name : String, email : String) {
        val user = getUser(token)
        val userId = user.id
        println("Email == " + email)
        return platziApi.updateUserData(userId, UserData(name, email))
    }


    // Notifications
    suspend fun addNotification(name : String, description : String, icon : Int){
        notificationsDao.insertNotification(NotificationEntity(name = name, description = description, iconType = icon))
    }
    suspend fun getNotifications(amount : Int, offset : Int) : List<NotificationEntity>{

        print("LOAD SIZE ++++ " + amount + "\n")
        print("PAGE NUMBER  ++++ " + offset + "\n")


        val notifications =  notificationsDao.getNotifications(limit = amount, offset = offset)
        println("All notif === \n" + notifications)
       return notifications
    }



    // Addresses
   suspend fun getCurrentAdress(): List<Address> {
        return addressDao.getAllAddresses().map {
            Address.entityToType(it)
        }
    }

    suspend fun selectAddress(id : Int){
        addressDao.selectAddress(id)
    }



    // Add new Card
    suspend fun addNewCard(name: String, number: String, cvv: Int, date: String) {
        return cardDao.insertCard(CardEntity(holdersName = name, number = number, cvv =  cvv, date = date ))
    }

    suspend fun getAllCards() : List<Card>{
        return cardDao.getAllCards().map{
            Card.toCard(it)
        }
    }


    // Clear
    suspend fun clearAllUserData(){
        notificationsDao.clearAllNotifications()
        ordersDao.clearAllOrders()
        favoriteProductsDao.clearAllFavoriteProducts()
        cartProductsDao.clearCart()
    }


    // Orders
    suspend fun addOrder(productID : Int, qty : Int, totalPrice : Long){
        return ordersDao.addOrder(OrderEntity(productID = productID, qty = qty, totalPrice = totalPrice))
    }
    suspend fun getAllOrders() : List<OrderWithProduct>{
        return ordersDao.getAllOrders().map {
            val product = getProduct(it.productID)
            val order = Order.toOrder(it)
            OrderWithProduct(order = order, product = product)
        }
    }



}