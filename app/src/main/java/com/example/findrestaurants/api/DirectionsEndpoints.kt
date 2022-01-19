package com.example.findrestaurants.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsEndpoints {
    @GET("json")
    fun getDirection(@Query("origin") start_latlng: String,
                     @Query("destination") end_latlng: String,
                     @Query("mode") mode: String,
                     @Query("key") key: String
    ): Call<Direction>
}