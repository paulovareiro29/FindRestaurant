package com.example.findrestaurants.recycler.dataclasses

data class Restaurant(
    val name: String,
    val price: Int,
    val rating: Float,
    val images: Array<Int>
)