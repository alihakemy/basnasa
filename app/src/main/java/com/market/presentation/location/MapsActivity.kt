package com.market.presentation.location


import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.google.android.gms.common.api.Status

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.market.R
import com.market.databinding.ActivityMapsBinding
import com.market.presentation.mainscreen.trader.TaderMainActivity
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.utils.LocationHelper
import com.market.utils.PermissionProvider
import com.market.utils.observeOnce
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var mlocation: Location? = null
    val viewModel: MapViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var coder = Geocoder(this)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(com.market.R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val permissionProvider = PermissionProvider(this@MapsActivity, this)
        permissionProvider.requestLocationPermission()

        val location = LocationHelper.getInstance(this)
        permissionProvider.locationPermissionGranted.observe(this, androidx.lifecycle.Observer {

            location.getLocation()

        })
        location.locatioLiveData.observeOnce(this, Observer {
            it?.let { it1 ->
                showLocation(it1)

            }
            Log.e("LOCATIOMIAL", it?.longitude.toString())

        })
        binding.location.setOnClickListener {
            location.getLocation()

        }

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(
            listOf(
                Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG
            )
        )

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                val loc = Location(LocationManager.GPS_PROVIDER)

                place.latLng?.let {
                    loc.latitude = it.latitude
                    loc.longitude = it.longitude
                    showLocation(loc)
                } ?: let {
                    val addressList: List<Address> = coder.getFromLocationName(place.name, 5)
                    if (!addressList.isNullOrEmpty()) {
                        val location: Address = addressList[0]
                        loc.latitude = location.latitude
                        loc.longitude = location.longitude
                        showLocation(loc)

                    }
                }


            }

            override fun onError(status: Status) {

            }
        })



        binding.button.setOnClickListener {
            mlocation?.let {
                viewModel.storeLocation(it.latitude.toString(), it.longitude.toString())

                if (intent.getStringExtra("role")?.toLowerCase().equals("tager")) {
                    val intent = Intent(baseContext, TaderMainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    ActivityCompat.finishAffinity(this)


                } else if (intent.getStringExtra("role").equals("location")) {

                    onBackPressed()
                } else {
                    val intent = Intent(baseContext, MainActivityUser::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    ActivityCompat.finishAffinity(this)
                }


            } ?: let {
                Toast.makeText(this, "اختر موقعك", Toast.LENGTH_LONG).show()

            }

        }

    }

    private fun showLocation(it: Location) {
        mlocation = it
        val sydney = LatLng(it.latitude, it.longitude)
        mMap.addMarker(MarkerOptions().position(sydney))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        val cameraPosition = CameraPosition.Builder()
            .target(
                LatLng(
                    it.latitude,
                    it.longitude
                )
            ) // Sets the center of the map to location user
            .zoom(17f) // Sets the zoom
            .build() // Creates a CameraPosition from the builder

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}