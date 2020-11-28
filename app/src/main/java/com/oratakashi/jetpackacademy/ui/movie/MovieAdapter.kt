package com.oratakashi.jetpackacademy.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.utils.ImageHelper
import kotlinx.android.synthetic.main.adapter_movie.view.*

class MovieAdapter(
    private val parent: MovieInterface
) : PagedListAdapter<DataMovie, MovieAdapter.ViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_movie,
            parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvTitle.text = getItem(position)?.title
            ImageHelper.getPicasso(
                it.ivImage,
                BuildConfig.IMAGE_URL + getItem(position)?.poster_path
            )

            it.setOnClickListener {
                parent.onClickMenu(getItem(position)!!)
            }
        }
    }

    companion object{
        private val DIFF_CALLBACK = object: DiffUtil.ItemCallback<DataMovie>(){
            override fun areContentsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: DataMovie, newItem: DataMovie): Boolean {
                return oldItem == newItem
            }
        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}