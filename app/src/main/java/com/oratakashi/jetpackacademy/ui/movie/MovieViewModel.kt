package com.oratakashi.jetpackacademy.ui.movie

import androidx.lifecycle.ViewModel
import com.oratakashi.jetpackacademy.data.DataMovie
import com.oratakashi.jetpackacademy.utils.DataDummy

class MovieViewModel : ViewModel() {
    fun getData(): List<DataMovie> {
        val data: MutableList<DataMovie> = ArrayList()
        val json = DataDummy.movieData

        for (i in json.indices) {
            val values = json[i]
            data.add(
                DataMovie(
                    values[0],
                    values[1],
                    values[2],
                    values[3],
                    values[4],
                    values[5],
                    values[6]
                )
            )
        }

        return data
    }
}