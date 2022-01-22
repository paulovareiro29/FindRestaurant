package com.example.findrestaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.example.findrestaurants.slider.ImageSliderAdapter


class ImageSliderActivity : AppCompatActivity() {

    lateinit var images: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_slider)

        images = intent.getStringArrayExtra("images")!!

        findViewById<ViewPager>(R.id.slider).adapter =  ImageSliderAdapter(applicationContext, images)

    }



    fun goBack(v: View){
        finish()
    }
}