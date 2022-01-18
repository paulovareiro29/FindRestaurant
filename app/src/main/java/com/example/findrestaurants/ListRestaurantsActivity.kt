package com.example.findrestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findrestaurants.api.Endpoints
import com.example.findrestaurants.api.ServiceBuilder
import com.example.findrestaurants.api.models.PlacesClass
import com.example.findrestaurants.recycler.RestaurantAdapter
import com.example.findrestaurants.recycler.dataclasses.Restaurant
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListRestaurantsActivity : AppCompatActivity() {


    private val SEARCH_RADIUS = 1500;
    private lateinit var location: LatLng

    protected var restaurantsList = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_restaurants)

        location = LatLng(intent.getDoubleExtra("latitude",0.0),
                            intent.getDoubleExtra("longitude",0.0))

        loadSpinners()
        loadRestaurantsList()
    }

    fun gotoHome(v: View?){
        startActivity(Intent(applicationContext, MainActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
    }

    fun loadSpinners(){
        val ratingSpinner: Spinner = findViewById(R.id.sort_rating_spinner)
        val priceSpinner: Spinner = findViewById(R.id.sort_price_spinner)

        ArrayAdapter(
            this,
            R.layout.spinner_item,
            arrayOf("Sort by rating", "High to Low", "Low to High")
        ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                ratingSpinner.adapter = adapter
            }



        ArrayAdapter(
            this,
            R.layout.spinner_item,
            arrayOf("Sort by price", "High to Low", "Low to High")
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priceSpinner.adapter = adapter
        }

        //  Item Selection Listeners
        ratingSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){
                    sortRestaurants(0,position-1)
                }
            }
        }

        priceSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position != 0){
                    sortRestaurants(1,position-1)
                }
            }
        }
    }

    fun loadRestaurantsList(){
        val request = ServiceBuilder.buildService(Endpoints::class.java)
        val call = request.getNearbyPlaces("${location.latitude},${location.longitude}",SEARCH_RADIUS,"restaurant",resources.getString(R.string.API_KEY))


        call.enqueue(object : Callback<PlacesClass> {
            override fun onResponse(
                call: Call<PlacesClass>,
                response: Response<PlacesClass>
            ) {
                if(response.isSuccessful){
                    for(i in 0..response.body()!!.results.size-1){
                        val currentPlace = response.body()!!.results[i]

                        if(currentPlace.rating >= 4.5F){
                            restaurantsList.add(Restaurant(currentPlace.name,currentPlace.price_level,currentPlace.rating, arrayOf(R.drawable.sample,R.drawable.sample,R.drawable.sample)))
                        }

                    }

                    loadRestaurantsRecyclerView(restaurantsList)

                }
            }

            override fun onFailure(call: Call<PlacesClass>, t: Throwable) {
                Log.d("DEBUG", "${t.message}")
            }
        })
    }

    fun sortRestaurants(type: Int, direction: Int) {
        /*
        * type:
        *   0: rating
        *   1: price
        * direction:
        *   0: high to low
        *   1: low to high
        * */
        Log.d("DEBUG",""+direction)
        var temporaryList = listOf<Restaurant>()
        if(type == 0){
            if(direction == 1){
                temporaryList = restaurantsList.sortedBy { it.rating }
            }else{
                temporaryList = restaurantsList.sortedByDescending { it.rating }
            }
        }else{
            if(direction == 1){
                temporaryList = restaurantsList.sortedBy { it.price }
            }else{
                temporaryList = restaurantsList.sortedByDescending { it.price }
            }
        }

        loadRestaurantsRecyclerView(temporaryList)

    }

    fun loadRestaurantsRecyclerView(restaurants: List<Restaurant>){
        val recycler = findViewById<RecyclerView>(R.id.restaurants_recycler)

        recycler.adapter = RestaurantAdapter(restaurants)
        recycler.layoutManager = LinearLayoutManager(this)
    }
}