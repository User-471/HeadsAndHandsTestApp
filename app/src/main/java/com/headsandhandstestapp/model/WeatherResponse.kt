package com.headsandhandstestapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherResponse(
    val location: WeatherLocation,
    val current: WeatherCurrent): Parcelable