package com.oratakashi.jetpackacademy.ui.tv

import com.oratakashi.jetpackacademy.data.model.tv.ResponseTv

sealed class TvState {
    object Loading : TvState()

    data class Result(val data : ResponseTv) : TvState()
    data class Error(val error : Throwable) : TvState()
}