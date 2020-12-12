package com.oratakashi.jetpackacademy.ui.tv.detail

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.oratakashi.jetpackacademy.BuildConfig
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.utils.Converter
import com.oratakashi.jetpackacademy.utils.ImageHelper
import com.oratakashi.jetpackacademy.utils.ImageOrientation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail_tv.*

@AndroidEntryPoint
class DetailTvActivity : AppCompatActivity() {

    private val data : DataTv? by lazy {
        intent.getParcelableExtra<DataTv>("data")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(window) {
            requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

            // set an exit transition
            enterTransition = Slide().setDuration(500)
            exitTransition = Fade().setDuration(850)
        }

        setContentView(R.layout.activity_detail_tv)

        if(data != null){
            tvTitle.text = data?.name
            tvDescription.text = data?.overview
            tvReleaseDate.text = Converter.dateFormat(
                data?.first_air_date!!,
                "yyyy-mm-dd",
                "dd MMMM yyyy"
            )

            ImageHelper.getPicasso(
                ivImage,
                BuildConfig.IMAGE_URL + data?.backdrop_path,
                ImageOrientation.Horizontal
            )
            ImageHelper.getPicasso(
                ivPhoto,
                BuildConfig.IMAGE_URL + data?.poster_path,
                ImageOrientation.Vertical
            )

            ivFav.setOnClickListener {
                viewModel.add(data!!)
            }

            viewModel.state.observe(this, Observer { when(it){
                true    -> ivFav.setBackgroundResource(R.drawable.ic_fav_on)
                false   -> ivFav.setBackgroundResource(R.drawable.ic_fav_off)
            } })
            viewModel.check(data!!)
        }else{
            finish()
        }

        fab.setOnClickListener { finish() }
    }

    private val viewModel : DetailTvViewModel by viewModels()
}