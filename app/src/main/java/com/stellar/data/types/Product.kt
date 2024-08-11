    package com.stellar.data.types

    import com.stellar.data.Category
    import com.stellar.data.dto.ProductDto


    data class Product(
        val id : Int,
        val title : String,
        val price : Long,
        val description : String,
        val category: Category,
        val images : List<String>,
        val color : String? = null,
        val creationAt : String? = null,
        val updatedAt : String? = null,
        )
    {

        companion object{
            fun toProduct(productDto : ProductDto) : Product{
                return Product(
                    id = productDto.id,
                    title =  productDto.title,
                    price =  productDto.price,
                    description = productDto.description,
                    category = productDto.category,
                    images = productDto.images.map {
                        it.replace("\"", "").replace("[", "")
                    },
                )
            }
        }


    }

    /*
    {


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

    }*/