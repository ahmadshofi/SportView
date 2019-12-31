package com.ahmad.sportview.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SportsItem(
	val idSport: String? = null,
	val strFormat: String? = null,
	val strSport: String? = null,
	val strSportThumb: String? = null,
	val strSportDescription: String? = null
) : Parcelable
