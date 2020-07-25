package com.headsandhandstestapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherCurrent(val temperature: Int): Parcelable