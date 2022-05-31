package com.market.presentation.location

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.market.R
import com.market.databinding.ActivityMapsBinding
import com.market.presentation.mainscreen.user.MainActivityUser
import com.market.utils.LocationHelper
import com.market.utils.PermissionProvider


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.location.setOnClickListener {
            val permissionProvider: PermissionProvider = PermissionProvider(this, this)
            permissionProvider.requestLocationPermission()
            permissionProvider.locationPermissionGranted.observe(this, androidx.lifecycle.Observer {
                LocationHelper.getInstance(context = baseContext, this)
                    .getLocation {

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

            })

        }
        binding.button.setOnClickListener {
            val intent = Intent(baseContext, MainActivityUser::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            ActivityCompat.finishAffinity(this)

        }



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