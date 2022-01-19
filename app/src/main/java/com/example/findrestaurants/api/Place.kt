package com.example.findrestaurants.api.models

import android.location.Location
import com.google.maps.android.data.Geometry

data class PlacesClass (

    val results: Array<Restaurant>

)

data class Restaurant(
    val name: String,
    val rating: Float,
    val price_level: Int,
    val geometry: Geo,
    val photos: List<PlacePhoto>,
)
data class PlacePhoto (
    val photo_reference: String,
)

data class Geo (
    val location: Loc,
)

data class Loc (
    val lat: Double,
    val lng: Double
)