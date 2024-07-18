    package com.stellar.data

    import kotlinx.serialization.Serializable



    data class Product(

        val id : Int,
        val title : String,
        val price : Long,
        val description : String,
        val category: Category,
        val images : List<String>,
        var favorite : Boolean

        //val creationAt : String,
        //val updatedAt : String,
    )