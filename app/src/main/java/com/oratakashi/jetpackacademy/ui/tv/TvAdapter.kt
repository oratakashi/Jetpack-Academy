package com.oratakashi.jetpackacademy.ui.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.utils.ImageHelper
import kotlinx.android.synthetic.main.adapter_tv.view.*

class TvAdapter (
    private val parent: TvInterface
): PagedListAdapter<DataTv, TvAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_tv,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            holder.itemView.also {
                it.tvTitle.text = getItem(position)!!.name
                ImageHelper.getPicassoCompress(
                    it.ivImage,
                    BuildConfig.IMAGE_URL + getItem(position)!!.poster_path
                )
                it.setOnClickListener { parent.onClickMenu(getItem(position)!!) }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DataTv>(){
            override fun areContentsTheSame(oldItem: DataTv, newItem: DataTv): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: DataTv, newItem: DataTv): Boolean {
                return oldItem == newItem
            }
        }
    }
}