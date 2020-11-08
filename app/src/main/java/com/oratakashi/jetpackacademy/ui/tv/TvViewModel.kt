package com.oratakashi.jetpackacademy.ui.tv

import androidx.lifecycle.ViewModel
import com.oratakashi.jetpackacademy.data.DataTv
import com.oratakashi.jetpackacademy.utils.DataDummy

class TvViewModel : ViewModel() {
    fun getData(): List<DataTv> {
        val data: MutableList<DataTv> = ArrayList()
        val json = DataDummy.tvShowData

        for (i in json.indices) {
            val values = json[i]
            data.add(
                DataTv(
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