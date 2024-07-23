    package com.stellar.data

    import com.stellar.data.db.entetities.ProductEntity



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
    ){


        companion object{
            fun toProduct(productEntity : ProductEntity) : Product{


                return Product(
                    productEntity.id,
                    productEntity.title,
                    productEntity.price,
                    productEntity.description,
                    Category(name = productEntity.category),
                    listOf(productEntity.images),
                    productEntity.favorite
                )
            }
        }

    }