package com.oratakashi.jetpackacademy.data.model.tv

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "tv")
data class DataTv (
    @SerializedName("backdrop_path") val backdrop_path : String?,
    @SerializedName("id") @PrimaryKey val id : String,
    @SerializedName("overview") val overview : String?,
    @SerializedName("poster_path") val poster_path : String?,
    @SerializedName("name") val name : String?,
    @SerializedName("first_air_date") val first_air_date : String?
) : Parcelable