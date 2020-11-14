package com.oratakashi.jetpackacademy.ui.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.utils.ImageHelper
import kotlinx.android.synthetic.main.adapter_tv.view.*

class TvAdapter(
    private val data: List<DataTv>,
    private val parent: TvInterface
) : RecyclerView.Adapter<TvAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvTitle.text = data[position].name
            ImageHelper.getPicassoCompress(
                it.ivImage,
                BuildConfig.IMAGE_URL + data[position].poster_path
            )
            it.setOnClickListener { parent.onClickMenu(data[position]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_tv,
            parent, false
        )
    )

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}