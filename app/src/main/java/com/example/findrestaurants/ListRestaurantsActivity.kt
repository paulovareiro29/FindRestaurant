package com.example.findrestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.findrestaurants.recycler.RestaurantAdapter
import com.example.findrestaurants.recycler.dataclasses.Restaurant

class ListRestaurantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_restaurants)

        loadSpinners()
        loadRestaurants()
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
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                ratingSpinner.adapter = adapter
            }

        ArrayAdapter(
            this,
            R.layout.spinner_item,
            arrayOf("Sort by price", "High to Low", "Low to High")
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            priceSpinner.adapter = adapter
        }
    }

    fun loadRestaurants(){
        val recycler = findViewById<RecyclerView>(R.id.restaurants_recycler)
        val restaurantsList = mutableListOf(
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
            Restaurant("Mom's Kitchen Nybrogatan",50f, 5.0f),
        )

        recycler.adapter = RestaurantAdapter(restaurantsList)
        recycler.layoutManager = LinearLayoutManager(this)
    }
}