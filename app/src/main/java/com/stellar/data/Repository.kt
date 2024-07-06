package com.stellar.data


val items = listOf(
    ItemEntity("Gucci bag", "Gucci", 1000),
    ItemEntity("Lui bag", "Gucci", 1),
ItemEntity("Nike Shoes", "Nike", 984),
ItemEntity("Abibas trainers", "Abibas", 178),
ItemEntity("POX", "Mister", 290),
ItemEntity("Legend", "ddd", 10),
ItemEntity("bistrov", "Bistrov Shoppping", 3230),
ItemEntity("Diaor Sunglassses", "Dior", 9000),


)

class Repository(){

    fun getNewArrilvals() : List<ItemEntity>{
        return items
    }



}