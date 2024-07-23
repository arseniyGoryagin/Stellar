package com.stellar.data

import com.stellar.data.db.entetities.ProductEntity

class PopularProduct(
    val product : Product,
    val type : String,
    val searches : String) {
}