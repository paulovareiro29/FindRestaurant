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

class RestaurantSliderAdapter(var context: Context, var images: Array<Int>) : PagerAdapter(){
    lateinit var layoutInflater: LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.slider_item, container, false)

        for (i in 0..2){
            val imageView = view.findViewById<ImageView>(container.context.resources.getIdentifier("image" + i, "id", container.context.packageName))
            imageView.setImageResource(images[position])
            container.addView(view)
        }

        return view
    }

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}