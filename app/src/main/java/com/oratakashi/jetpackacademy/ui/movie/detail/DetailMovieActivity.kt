package com.oratakashi.jetpackacademy.ui.movie.detail

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.ImageHelper
import com.oratakashi.jetpackacademy.utils.ImageOrientation
import kotlinx.android.synthetic.main.activity_detail_movie.*

class DetailMovieActivity : AppCompatActivity() {

    private val data : DataMovie? by lazy {
        intent.getParcelableExtra<DataMovie>("data")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            // set an exit transition
            enterTransition = Slide().setDuration(500)
            exitTransition = Fade().setDuration(850)
        }

        setContentView(R.layout.activity_detail_movie)

        if(data != null){
            tvTitle.text = data?.title
            tvDescription.text = data?.overview
            tvReleaseDate.text = Converter.dateFormat(
                data?.release_date!!,
                "yyyy-mm-dd",
                "dd MMMM yyyy"
            )
            ImageHelper.getPicasso(
                ivPhoto,
                BuildConfig.IMAGE_URL + data?.poster_path,
                ImageOrientation.Vertical
            )
            ImageHelper.getPicasso(
                ivImage,
                BuildConfig.IMAGE_URL + data?.backdrop_path,
                ImageOrientation.Horizontal
            )
        }else{
            finish()
        }


        fab.setOnClickListener { finish() }
    }
}