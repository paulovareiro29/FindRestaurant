package com.example.findrestaurants


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.findrestaurants.api.Direction
import com.example.findrestaurants.api.DirectionsEndpoints
import com.example.findrestaurants.api.DirectionsServiceBuilder

import com.example.findrestaurants.databinding.ActivityMapsBinding
import com.google.android.gms.location.*

import com.google.android.gms.maps.*
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.PolyUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private lateinit var lastLocation: Location
    private lateinit var restaurantLocation: LatLng

    private var directionsMode: String = "driving"
    private lateinit var polylinePoints: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        restaurantLocation = LatLng(intent.getDoubleExtra("latitude",0.0),
                                    intent.getDoubleExtra("longitude",0.0))

        /*locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                lastLocation = locationResult.lastLocation
            }
        }

        createLocationRequest()*/


    }

    fun gotoHome(v: View?) {
        startActivity(
            Intent(
                applicationContext,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }


    fun initMap(){

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        mMap.isMyLocationEnabled = true

        fusedLocationClient.lastLocation.addOnSuccessListener(this){ location ->
            if(location != null) {
                lastLocation = location

                val currentLatLng = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 16f))

                fetchDirection()

                //startLocationUpdates()
            }
        }
    }

    fun startLocationUpdates() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun createLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest.interval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    fun fetchDirection(){
        val request = DirectionsServiceBuilder.buildService(DirectionsEndpoints::class.java)
        val call = request.getDirection("${lastLocation.latitude},${lastLocation.longitude}",
            "${restaurantLocation.latitude},${restaurantLocation.longitude}",
            directionsMode,resources.getString(R.string.API_KEY))

        call.enqueue(object : Callback<Direction> {
            override fun onResponse(call: Call<Direction>, response: Response<Direction>){
                if(response.isSuccessful){
                    polylinePoints = response.body()!!.routes[0].overview_polyline.points
                    mMap.clear()
                    showInfo()
                }
            }

            override fun onFailure(call: Call<Direction>, t: Throwable){
                Toast.makeText(applicationContext,"Error loading directions", Toast.LENGTH_LONG).show()
                Log.d("DirectionsAPI", "${t.message}")
            }
        })
    }

    fun showInfo(){
        var decodedPath: List<LatLng> = PolyUtil.decode(polylinePoints)
        val polyline = mMap.addPolyline(PolylineOptions().color(resources.getColor(R.color.primary)).addAll(decodedPath))

        polyline.jointType = JointType.ROUND

        mMap.addMarker(
            MarkerOptions().position(restaurantLocation))
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        initMap()
    }

    fun changeToWalking(v: View?){
        directionsMode = "walking"

        findViewById<RelativeLayout>(R.id.direction_driving_container).background.setTint(resources.getColor(R.color.white))
        findViewById<ImageView>(R.id.direction_driving_icon).setImageResource(R.drawable.icon_driving_inactive)
        findViewById<TextView>(R.id.direction_driving_time).setTextColor(resources.getColor(R.color.primary_900))

        findViewById<RelativeLayout>(R.id.direction_walking_container).background.setTint(resources.getColor(R.color.primary))
        findViewById<ImageView>(R.id.direction_walking_icon).setImageResource(R.drawable.icon_walk_active)
        findViewById<TextView>(R.id.direction_walking_time).setTextColor(resources.getColor(R.color.white))

        fetchDirection()
    }

    fun changeToDriving(v: View?){
        directionsMode = "driving"

        findViewById<RelativeLayout>(R.id.direction_walking_container).background.setTint(resources.getColor(R.color.white))
        findViewById<ImageView>(R.id.direction_walking_icon).setImageResource(R.drawable.icon_walk_inactive)
        findViewById<TextView>(R.id.direction_walking_time).setTextColor(resources.getColor(R.color.primary_900))

        findViewById<RelativeLayout>(R.id.direction_driving_container).background.setTint(resources.getColor(R.color.primary))
        findViewById<ImageView>(R.id.direction_driving_icon).setImageResource(R.drawable.icon_driving_active)
        findViewById<TextView>(R.id.direction_driving_time).setTextColor(resources.getColor(R.color.white))


        fetchDirection()
    }

}