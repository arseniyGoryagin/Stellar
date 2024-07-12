package com.stellar.data

data class SearchFilter(
    val categoryId : Int? = null,
    val price_min :Float = MIN_RANGE,
    val price_max : Float = MAX_RANGE
){
    companion object{
        const val MAX_RANGE : Float= 10000F
        const val MIN_RANGE: Float = 0F
    }
}