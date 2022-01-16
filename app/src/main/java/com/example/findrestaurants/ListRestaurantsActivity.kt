package com.example.findrestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findrestaurants.recycler.RestaurantAdapter
import com.example.findrestaurants.recycler.dataclasses.Restaurant

class ListRestaurantsActivity : AppCompatActivity() {

    protected val restaurantsList = listOf<Restaurant>(
        Restaurant("Mom's Kitchen Nybrogatan",45f, 5f, arrayOf(R.drawable.sample,R.drawable.sample)),
        Restaurant("Mom's Kitchen Nybrogatan",46f, 4.6f, arrayOf(R.drawable.sample,R.drawable.sample)),
        Restaurant("Mom's Kitchen Nybrogatan",48f, 4.2f, arrayOf(R.drawable.sample,R.drawable.sample)),
        Restaurant("Mom's Kitchen Nybrogatan",40f, 3.6f, arrayOf(R.drawable.sample,R.drawable.sample)),
        Restaurant("Mom's Kitchen Nybrogatan",50f, 4.7f, arrayOf(R.drawable.sample,R.drawable.sample))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_restaurants)

        loadSpinners()
        loadRestaurantsRecyclerView(restaurantsList)
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