package com.example.findrestaurants.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.findrestaurants.R
import com.example.findrestaurants.recycler.dataclasses.Restaurant

class RestaurantAdapter(
    var restaurants: List<Restaurant>
) : RecyclerView.Adapter<RestaurantViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.apply {
            name.text = restaurant.name
            price.text = restaurant.price.toString()
            rating.text = restaurant.rating.toString()
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }
}

class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val name = itemView.findViewById<TextView>(R.id.name)
    val price = itemView.findViewById<TextView>(R.id.price)
    val rating = itemView.findViewById<TextView>(R.id.rating)

}