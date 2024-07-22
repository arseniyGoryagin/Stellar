package com.stellar.data

import kotlinx.serialization.Serializable


@Serializable
data class Category(
    val id : Int,
    val name: String,
    val image: String
){}

@Serializable
data class BaseProduct(

    val id : Int,
    val title : String,
    val price : Long,
    val description : String,
    val category: Category,
    val images : List<String>,


    //val creationAt : String,
    //val updatedAt : String,
){
    companion object {

        fun BaseProductToProduct(baseProduct : BaseProduct, favorite : Boolean = false) : Product{

            val product = Product(
                baseProduct.id,
                baseProduct.title,
                baseProduct.price,
                baseProduct.description,
                baseProduct.category,
                baseProduct.images,
                favorite
                )


            return product
        }

    }
}