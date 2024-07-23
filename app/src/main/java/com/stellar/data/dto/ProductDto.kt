package com.stellar.data.dto

import com.stellar.data.Category
import com.stellar.data.Product
import com.stellar.data.db.entetities.ProductEntity
import kotlinx.serialization.Serializable



@Serializable
data class ProductDto(

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

        fun ProductDtoToProduct(baseProduct : ProductDto, favorite : Boolean = false) : Product {

            return Product(
                baseProduct.id,
                baseProduct.title,
                baseProduct.price,
                baseProduct.description,
                baseProduct.category,
                baseProduct.images,
                favorite
                )


        }


        fun ProductDtoToProductEntity(baseProduct : ProductDto, favorite : Boolean = false) : ProductEntity{

            return ProductEntity(
                baseProduct.id,
                baseProduct.title,
                baseProduct.price,
                baseProduct.description,
                baseProduct.category.name,
                baseProduct.images[0],
                favorite
            )


        }

    }
}