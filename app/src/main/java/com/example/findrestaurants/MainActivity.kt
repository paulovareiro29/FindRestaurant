package com.example.findrestaurants


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val LOCATION_PERMISSION_REQUEST_CODE = 9999

    var selectedLocation: LatLng? = null

    companion object {
        var mPermissionsGranted: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getLocationPermissions()
        Log.d("DEBUG","TESTT")


        Places.initialize(applicationContext,resources.getString(R.string.API_KEY))
        Places.createClient(this)

        loadAutoComplete()
    }

    fun loadAutoComplete(){
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Remove icon
        autocompleteFragment.view?.findViewById<ImageView>(R.id.places_autocomplete_search_button)?.setImageDrawable(resources.getDrawable(R.drawable.icon_location))
        val searchInput = autocompleteFragment.view?.findViewById<EditText>(R.id.places_autocomplete_search_input)
        searchInput?.textSize = 6 * getResources().getDisplayMetrics().scaledDensity;

        autocompleteFragment.setHint("Type a location")
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME,Place.Field.LAT_LNG))

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i("DEBUG", "Place: ${place.latLng}")
                if(place.latLng != null){
                    selectedLocation=LatLng(place.latLng!!.latitude,place.latLng!!.longitude)
                }
            }

            override fun onError(status: Status) {
                Log.i("DEBUG", "An error occurred: $status")
            }
        })
    }

    // On share location btn click
    @SuppressLint("MissingPermission")
    fun shareLocation(v: View?){


        if(mPermissionsGranted){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)


            fusedLocationClient.getCurrentLocation(LocationRequest.QUALITY_HIGH_ACCURACY,CancellationTokenSource().token).addOnSuccessListener(this) { location ->
                if (location != null) {
                    selectedLocation = LatLng(location.latitude,location.longitude)
                    Toast.makeText(this,"Location loaded",Toast.LENGTH_LONG).show()
                }
            }
        }

    }


    // On search btn click
    fun searchLocation(v: View?){
       if(selectedLocation != null){
            startActivity(Intent(applicationContext, ListRestaurantsActivity::class.java)
                .putExtra("latitude",selectedLocation!!.latitude)
                .putExtra("longitude",selectedLocation!!.longitude))
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
