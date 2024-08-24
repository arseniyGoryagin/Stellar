    package com.stellar.data.types

    import android.os.Build
    import androidx.annotation.RequiresApi
    import com.stellar.data.dto.ProductDto
    import java.time.format.DateTimeFormatter


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
            @RequiresApi(Build.VERSION_CODES.O)
            fun toProduct(productDto : ProductDto) : Product{

                val formater = DateTimeFormatter.ISO_DATE_TIME

                return Product(
                    id = productDto.id,
                    title =  productDto.title,
                    price =  productDto.price,
                    description = productDto.description,
                    category = productDto.category,
                    creationAt = productDto.creationAt,
                    updatedAt = productDto.updatedAt,
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