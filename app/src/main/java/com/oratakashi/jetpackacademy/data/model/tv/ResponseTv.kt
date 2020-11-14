package com.oratakashi.jetpackacademy.data.model.tv

import com.google.gson.annotations.SerializedName

data class ResponseTv (
    @SerializedName("results") val data : List<DataTv>?,
    @SerializedName("page") val page : Int?,
    @SerializedName("total_pages") val total_pages : Int?
)