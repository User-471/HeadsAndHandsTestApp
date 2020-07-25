package com.headsandhandstestapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ConnectionModel(val type: Int,
                           val isConnected: Boolean): Parcelable