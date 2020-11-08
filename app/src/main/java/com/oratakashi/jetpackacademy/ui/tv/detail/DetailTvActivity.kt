package com.oratakashi.jetpackacademy.ui.tv.detail

import android.os.Bundle
import android.transition.Fade
import android.transition.Slide
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.DataTv
import com.oratakashi.jetpackacademy.utils.ImageHelper
import kotlinx.android.synthetic.main.activity_detail_tv.*

class DetailTvActivity : AppCompatActivity() {

    val data: DataTv by lazy {
        intent.getParcelableExtra<DataTv>("data")!!
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

        val photo = resources.getIdentifier(
            "com.oratakashi.jetpackacademy:drawable/"
                    + data.img.substring(10, data.img.length),
            null, null
        )

        ImageHelper.getDrawable(ivImage, photo)
        ImageHelper.getDrawable(ivPhoto, photo)

        tvTitle.text = data.title.trim()
        tvReleaseDate.text = data.date
        tvDuration.text = data.duration
        tvLanguage.text = data.language
        tvDescription.text = data.description

        fab.setOnClickListener { finish() }
    }
}