package com.example.findrestaurants.recycler.dataclasses

import com.google.android.gms.maps.model.LatLng

data class Restaurant(
    val name: String,
    val price: Int,
    val rating: Float,
    val latLng: LatLng,
    val images: MutableList<String>
)