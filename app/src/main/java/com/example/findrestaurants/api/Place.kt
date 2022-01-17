package com.example.findrestaurants.api.models

data class Places (

    val results: Array<Restaurant>

)

data class Restaurant(
    val name: String,
    val rating: Float,
    val price_level: Int,
    val coords: Coordinates
)

data class Coordinates (
    val latitude: Float,
    val longitude: Float
)