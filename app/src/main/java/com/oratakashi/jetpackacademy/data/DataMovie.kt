package com.oratakashi.jetpackacademy.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataMovie(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val duration: String,
    val language: String,
    val img: String
) : Parcelable