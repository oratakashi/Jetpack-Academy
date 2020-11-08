package com.oratakashi.jetpackacademy.ui.movie

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.DataMovie
import com.oratakashi.jetpackacademy.ui.main.MainInterface
import com.oratakashi.jetpackacademy.ui.movie.detail.DetailMovieActivity
import kotlinx.android.synthetic.main.fragment_movie.*


class MovieFragment : Fragment(), MainInterface.Fragment, MovieInterface {

    private val data: MutableList<DataMovie> by lazy {
        ArrayList<DataMovie>()
    }

    private val adapter: MovieAdapter by lazy {
        MovieAdapter(data, this)
    }

    private val viewModel: MovieViewModel by lazy {
        ViewModelProviders.of(this).get(MovieViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        parent.registerFragment(this)

        rvMovie.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
        }

        data.addAll(viewModel.getData())
        adapter.notifyItemInserted(0)

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
    }

    override fun onClickMenu(data: DataMovie) {
        startActivity(
            Intent(requireContext(), DetailMovieActivity::class.java).also {
                it.putExtra("data", data)
            }, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
        )
    }
}