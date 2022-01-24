package com.example.findrestaurants.api

import com.example.findrestaurants.api.models.PlaceDetails
import com.example.findrestaurants.api.models.PlacesClass
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface Endpoints {

    @GET("nearbysearch/json")
    fun getNearbyPlaces(@Query("key") key: String,
                        @Query("pagetoken") pagetoken: String): Call<PlacesClass>

    @GET("nearbysearch/json")
    fun getNearbyPlaces(@Query("location")  coordinates: String,
                        @Query("radius")  radius: Int,
                        @Query("type")  type: String,
                        @Query("key") key: String): Call<PlacesClass>

    @GET("details/json")
    fun getPlaceDetails(@Query("place_id")  placeID: String,
                        @Query("key") key: String,): Call<PlaceDetails>
}
