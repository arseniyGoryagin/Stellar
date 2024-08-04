package com.stellar.data.types

import com.stellar.data.db.entetities.CartProductEntity

data class CartProduct(
    val id : Long,
    val productID : Int,
    val qty : Int
){

    companion object{

        fun toCartProduct(product : CartProductEntity) : CartProduct{
            return CartProduct(
                id = product.id,
                productID = product.productID,
                qty = product.qty
            )

        }

    }

}