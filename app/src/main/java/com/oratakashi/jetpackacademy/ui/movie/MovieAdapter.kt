package com.oratakashi.jetpackacademy.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.utils.ImageHelper
import com.oratakashi.jetpackacademy.utils.ImageOrientation
import kotlinx.android.synthetic.main.adapter_movie.view.*

class MovieAdapter(
    private val data: List<DataMovie>,
    private val parent: MovieInterface
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>(), ImageHelper.Return {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvTitle.text = data[position].title
            it.shLoading.startShimmerAnimation()
            ImageHelper.getPicassoCompress(
                it.ivImage,
                BuildConfig.IMAGE_URL + data[position].poster_path,
                ImageOrientation.Vertical,
                this,
                it.shLoading
            )

            it.setOnClickListener {
                parent.onClickMenu(data[position])
            }
        }
    }

    override fun onImageLoaded(imageView: ImageView?, shimmerFrameLayout: ShimmerFrameLayout?) {
        imageView?.visibility = View.VISIBLE
        shimmerFrameLayout?.stopShimmerAnimation()
        shimmerFrameLayout?.visibility = View.GONE
    }

    override fun onImageFailed(error: String, shimmerFrameLayout: ShimmerFrameLayout?) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_movie,
            parent, false
        )
    )

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}