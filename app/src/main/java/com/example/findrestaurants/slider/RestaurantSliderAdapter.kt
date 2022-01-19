package com.example.findrestaurants.slider

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.example.findrestaurants.R
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide


class RestaurantSliderAdapter(var context: Context, var images: MutableList<String>) : PagerAdapter(){
    lateinit var layoutInflater: LayoutInflater

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.slider_item, container, false)

        for(s in images){
            Log.d("DEBUG", s)
        }


        if(images.size != 0){
            for (i in 0..2){

                if(images.size > 3*position + i){
                    val imageView = view.findViewById<ImageView>(container.context.resources.getIdentifier("image" + i, "id", container.context.packageName))
                    Log.d("DEBUG", "size: ${images.size}, pos: ${3*position + i}")
                    Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400"+
                            "&photo_reference=${images[ 3*position + i]}" +
                            "&key=${context.resources.getString(R.string.API_KEY)}").into(imageView);
                    container.addView(view)
                }

            }
        }


        return view
    }

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}