package com.oratakashi.jetpackacademy.utils

import android.util.Log
import android.widget.ImageView
import com.facebook.shimmer.ShimmerFrameLayout
import com.oratakashi.jetpackacademy.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

object ImageHelper {
    fun getDrawable(imageView: ImageView, image_url: Int) {
        Picasso.get().load(image_url)
            .placeholder(R.drawable.img_no_images)
            .error(R.drawable.img_no_images)
            .into(imageView, object : Callback {
                override fun onSuccess() {}
                override fun onError(e: Exception) {
                    Log.e("Picasso", "message : ${e.message}")
                    Log.e("Picasso_URl", image_url.toString())
                }
            })
    }

    fun getPicasso(
        imageView: ImageView,
        image_url: String?,
        callback: Return? = null,
        loading: ShimmerFrameLayout? = null
    ) {
        Picasso.get().load(image_url)
            .placeholder(R.drawable.img_no_images)
            .error(R.drawable.img_no_images)
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    callback?.onImageLoaded(imageView, loading)
                }

                override fun onError(e: Exception) {
                    Log.e("Picasso", e.message!!)
                    Log.e("Picasso_URl", image_url!!)
                    callback?.onImageFailed(e.message!!)
                }
            })
    }


    interface Return {
        fun onImageLoaded(
            imageView: ImageView? = null,
            shimmerFrameLayout: ShimmerFrameLayout? = null
        )

        fun onImageFailed(
            error: String,
            shimmerFrameLayout: ShimmerFrameLayout? = null
        )
    }
}