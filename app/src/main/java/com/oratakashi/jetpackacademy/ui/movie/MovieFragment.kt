package com.oratakashi.jetpackacademy.ui.movie

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.ui.main.MainInterface
import com.oratakashi.jetpackacademy.ui.movie.detail.DetailMovieActivity
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : Fragment(), MainInterface.Fragment, MovieInterface {

    private val adapter : MovieAdapter by lazy {
        MovieAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        parent.registerFragment(this)

        rvMovie.also {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.adapter = adapter
            it.isNestedScrollingEnabled = false
        }

        viewModel.getMovie()

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it){
                is MovieState.Loading   -> {
                    shLoading.visibility = View.VISIBLE
                    rvMovie.visibility = View.GONE
                    shLoading.startShimmerAnimation()
                    EspressoIdlingResource.increment()
                }
                is MovieState.Result    -> {
                    if(shLoading.isVisible){
                        shLoading.stopShimmerAnimation()
                        shLoading.visibility = View.GONE
                    }
                    if(!rvMovie.isVisible){
                        rvMovie.visibility = View.VISIBLE
                        EspressoIdlingResource.decrement()
                    }
                }
                is MovieState.Error     -> {
                    EspressoIdlingResource.decrement()
                    shLoading.visibility = View.GONE
                    shLoading.stopShimmerAnimation()
                    rvMovie.visibility = View.VISIBLE

                    dialog.setMessage(it.error.message)
                        .create()
                        .show()
                }
            }
        })
        viewModel.data.observe(viewLifecycleOwner, Observer(adapter::submitList))

        viewModel.setupSearch(etSearch)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    companion object {
        lateinit var parent: MainInterface.Activity

        @JvmStatic
        fun newInstance(callback: MainInterface.Activity) =
            MovieFragment().apply {
                parent = callback
            }
    }

    override fun setExpanded() {
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        bottom_sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    override fun onClickMenu(data: DataMovie) {
        startActivity(
            Intent(requireContext(), DetailMovieActivity::class.java).also {
                it.putExtra("data", data)
            }, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
        )
    }

    private val viewModel : MovieViewModel by viewModels()
    @Inject lateinit var dialog : AlertDialog.Builder
}