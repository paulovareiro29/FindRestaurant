package com.example.findrestaurants


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.findrestaurants.api.Endpoints
import com.example.findrestaurants.api.ServiceBuilder
import com.example.findrestaurants.api.models.Places
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val LOCATION_PERMISSION_REQUEST_CODE = 9999
    lateinit var lastLocation: Location


    companion object {
        var mPermissionsGranted: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocationPermissions()
        Log.d("DEBUG","TESTT")
    }

    @SuppressLint("MissingPermission")
    fun shareLocation(v: View?){


        if(mPermissionsGranted){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)


            fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,CancellationTokenSource().token).addOnSuccessListener(this) { location ->
                if (location != null) {
                    lastLocation = location

                    Toast.makeText(this,"Location loaded",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    fun searchLocation(v: View?){
        if(lastLocation != null){
            startActivity(Intent(applicationContext, ListRestaurantsActivity::class.java)
                .putExtra("latitude",lastLocation.latitude)
                .putExtra("longitude",lastLocation.longitude))
        }else{
            Toast.makeText(this,"No location loaded",Toast.LENGTH_LONG).show()

        }

    }

    fun getLocationPermissions(){
        val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

        var permissions = arrayOf(FINE_LOCATION, COARSE_LOCATION)

        if(ContextCompat.checkSelfPermission(applicationContext,FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(applicationContext,COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mPermissionsGranted = true
            }
        }

        if(!mPermissionsGranted){
            ActivityCompat.requestPermissions(this,permissions,LOCATION_PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if(grantResults.size > 0){
                    for(i in 0..grantResults.size-1){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            return
                        }
                    }
                }

                mPermissionsGranted = true
            }
        }
    }




}