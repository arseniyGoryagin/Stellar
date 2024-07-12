    package com.stellar.data

    import kotlinx.serialization.Serializable


    @Serializable
    data class Category(
        val id : Int,
        val name: String,
        val image: String
    ){}

    @Serializable
    data class Product(

        val id : Int,
        val title : String,
        val price : Int,
        val description : String,
        val category: Category,
        val images : List<String>,

        //val creationAt : String,
        //val updatedAt : String,
    )