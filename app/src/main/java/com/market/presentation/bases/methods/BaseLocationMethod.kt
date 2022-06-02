package com.market.presentation.bases.methods

interface BaseLocationMethod {
    fun getLocation(): Boolean
    fun getLatLong():Pair<String,String>

}