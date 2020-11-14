package com.oratakashi.jetpackacademy.data.model.tv

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataTv (
    @SerializedName("backdrop_path") val backdrop_path : String?,
    @SerializedName("id") val id : String?,
    @SerializedName("overview") val overview : String?,
    @SerializedName("poster_path") val poster_path : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("first_air_date") val first_air_date : String?
) : Parcelable