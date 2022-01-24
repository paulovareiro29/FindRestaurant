package com.example.findrestaurants.recycler

import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.findrestaurants.MapsActivity
import com.example.findrestaurants.R
import com.example.findrestaurants.recycler.dataclasses.Restaurant
import com.example.findrestaurants.slider.RestaurantSliderAdapter
import androidx.core.content.ContextCompat.startActivity




class RestaurantAdapter(
    var restaurants: List<Restaurant>
) : RecyclerView.Adapter<RestaurantViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]

        val sliderAdapter = RestaurantSliderAdapter(holder.itemView.context, restaurant.images)


        holder.apply {
            name.text = restaurant.name

            var link: String? = ""
            if(restaurant.website != null){
                link = restaurant.website
            }else if(restaurant.url != null){
                link = restaurant.url
            }else{
                link = null
            }

            if(link != null){
                name.setOnClickListener(View.OnClickListener {
                    val i2 = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(link)
                    )
                    startActivity(
                        holder.itemView.context,
                        i2,null
                    )
                })
            }



            rating.text = restaurant.rating.toString()

            var priceText = ""
            for(i in 0..restaurant.price){
                priceText += "$"
            }

            price.text = priceText


            if(stars.isEmpty()){
                // Value from DP to PX
                val starSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                    15F, holder.itemView.context.resources.getDisplayMetrics()).toInt()

                // Add rating stars
                for(i in 0..4) {
                    val star: ImageView = ImageView(holder.itemView.context)
                    stars.addView(star)

                    star.layoutParams.height = starSize
                    star.layoutParams.width = starSize

                    val ratingDiff = restaurant.rating - i
                    if(ratingDiff >= 1){
                        star.setImageResource(R.drawable.icon_star_fill)
                    }else if(ratingDiff > 0.5F ){
                        star.setImageResource(R.drawable.icon_star_half)
                    }else{
                        // Add empty star OR No star
                        stars.removeView(star)
                    }
                }
            }

            slider.adapter = sliderAdapter
            directions_btn.setOnClickListener {
                holder.itemView.context.startActivity(Intent(holder.itemView.context, MapsActivity::class.java)
                    .putExtra("latitude",restaurant.latLng.latitude)
                    .putExtra("longitude",restaurant.latLng.longitude))
            }
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
    val stars = itemView.findViewById<LinearLayout>(R.id.stars)
    val slider = itemView.findViewById<ViewPager>(R.id.slider)
    val directions_btn = itemView.findViewById<Button>(R.id.directions_btn)
}