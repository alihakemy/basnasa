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
import androidx.lifecycle.MutableLiveData

class LocationHelper(val activity: Activity) : LocationListener {


    private var locationManager: LocationManager = activity
        .getSystemService(Context.LOCATION_SERVICE) as LocationManager


    val locatioLiveData: MutableLiveData<Location?> = MutableLiveData()

    companion object {

        fun getInstance(activity: Activity): LocationHelper {
            return LocationHelper(activity)
        }

    }


    fun getLocation() {

        getLastKnowLocationGps()


        getLocationRealTime()

    }


    private fun getLastKnowLocationGps() {

        val isGPSEnabled = locationManager!!
            .isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
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
            locatioLiveData.postValue(location)


        } else {
            showSettingsAlert()
        }

    }


    private fun removeObserver() {
        locationManager.removeUpdates(this)
    }

    private fun getLocationRealTime() {


        val isGPSEnabled = locationManager
            .isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
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
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        location?.let {
            locatioLiveData.postValue(location)
        } ?: let {

            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    this
                )
            }

        }


    }


    fun showSettingsAlert() {


        val alertDialog = AlertDialog.Builder(
            activity
        )
        alertDialog.setTitle("GPS settings")
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?")
        alertDialog.setPositiveButton("Settings") { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)

        }
        alertDialog.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()

        }
        alertDialog.show()
    }

    override fun onLocationChanged(location: Location) {

        locatioLiveData.postValue(location)


        removeObserver()

        Log.i("test", "Latitute: $location.latitute ; Longitute: $location.longitute")

    }


}