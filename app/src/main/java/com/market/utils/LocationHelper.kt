package com.market.utils

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.tasks.CancellationTokenSource

class LocationHelper(val context: Context, val activity: Activity) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var locationManager: LocationManager = context
        .getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private var location: Location? = null


    companion object{

        fun getInstance(context: Context,activity: Activity): LocationHelper {
            return LocationHelper(context,activity)
        }

    }





    fun getLocation(getLocation: (location: Location) -> Unit) {
        getLastKnowLocation {
            location = it
        }
        if (location == null) {
            getLastKnowLocation_NETWORK_PROVIDER {
                location = it
            }
        }
        if (location == null) {
            getCurrentLocation {
                location = it
            }
        }

        if (location == null) {
            getLastKnowLocationGps {
                location = it
            }
        }

        location?.let { getLocation(it) }

    }


    private fun getLastKnowLocation_NETWORK_PROVIDER(lastKnownLocation_NETWORK_PROVIDER: (lastKnown_NETWORK_PROVIDER: Location) -> Unit) {

        val isNETWORK_PROVIDEREnabled = locationManager!!
            .isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        if (isNETWORK_PROVIDEREnabled) {

            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                ?.let { lastKnownLocation_NETWORK_PROVIDER(it) }
        }

    }

    private fun getLastKnowLocationGps(lastKnownLocationGPS: (lastKnown: Location) -> Unit) {

        val isGPSEnabled = locationManager!!
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        if (isGPSEnabled) {

            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
//            val location = locationManager.getCurrentLocation(LocationManager.GPS_PROVIDER)

            location?.let { lastKnownLocationGPS(it) }
        } else {
            showSettingsAlert()
        }

    }

    private fun getLastKnowLocation(lastKnownLocation: (lastKnown: Location) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations this can be null.
                location?.let { lastKnownLocation(it) }
            }
    }

    private fun getCurrentLocation(currentLocation: (currentLocation: Location) -> Unit) {
        val cancellationTokenSource = CancellationTokenSource()
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource.token
        ).addOnSuccessListener { location: Location? ->
            location?.let { currentLocation(it) }
        }
    }

    fun showSettingsAlert() {


        val alertDialog = AlertDialog.Builder(
            context
        )
        alertDialog.setTitle("GPS settings")
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        alertDialog.setPositiveButton("Settings") { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
            activity.onBackPressed()
        }
        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
            activity.onBackPressed()
        }
        alertDialog.show()
    }


    private lateinit var locationListener: LocationListener
    private fun removeObserver() {
        locationManager.removeUpdates(locationListener)
    }

    private fun getLocationRealTime() {
        if (location == null) {

            locationListener = LocationListener { location ->
                this.location = location
                var latitute = location!!.latitude
                var longitute = location!!.longitude

                Log.i("test", "Latitute: $latitute ; Longitute: $longitute")
                removeObserver()
            }
            //  getLocationRealTime()
        }
        val isGPSEnabled = locationManager!!
            .isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (isGPSEnabled) {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0L,
                0f,
                locationListener
            )
        }

    }


}