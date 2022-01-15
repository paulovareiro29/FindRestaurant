package com.example.findrestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun shareLocation(v: View?){
        startActivity(Intent(applicationContext, ListRestaurantsActivity::class.java))
    }

    fun searchLocation(v: View?){
        startActivity(Intent(applicationContext, ListRestaurantsActivity::class.java))
    }
}