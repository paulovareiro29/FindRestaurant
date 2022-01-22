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


class ImageSliderAdapter(var context: Context, var images: Array<String>) : PagerAdapter(){
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
        val view = layoutInflater.inflate(R.layout.image_slider_item, container, false)

        if(images.size != 0){
            for (i in 0..images.size){
                Log.d("DEBUG","" + images[position])
                    val imageView = view.findViewById<ImageView>(R.id.slider_image)
                    Glide.with(context).load("https://maps.googleapis.com/maps/api/place/photo?maxwidth=2000"+
                            "&photo_reference=${images[position]}" +
                            "&key=${context.resources.getString(R.string.API_KEY)}").into(imageView);
                    if (view.getParent() != null) {
                        (view.getParent() as ViewGroup).removeView(view) // <- fix
                    }
                    container.addView(view)

            }
        }

        return view
    }

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}