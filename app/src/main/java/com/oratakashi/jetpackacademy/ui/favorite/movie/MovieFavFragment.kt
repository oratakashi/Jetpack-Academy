package com.oratakashi.jetpackacademy.ui.favorite.movie

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.ui.movie.MovieAdapter
import com.oratakashi.jetpackacademy.ui.movie.MovieInterface
import com.oratakashi.jetpackacademy.ui.movie.MovieViewModel
import com.oratakashi.jetpackacademy.ui.movie.detail.DetailMovieActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie_fav.*

@AndroidEntryPoint
class MovieFavFragment : Fragment(), MovieInterface {

    private val adapter : MovieAdapter by lazy {
        MovieAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFavMovie.also {
            it.adapter = adapter
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.data.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.setupSearch(etSearch)
        viewModel.getMovie()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_fav, container, false)
    }

    override fun onClickMenu(data: DataMovie) {
        startActivity(
            Intent(requireContext(), DetailMovieActivity::class.java).also {
                it.putExtra("data", data)
            }, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
        )
    }

    private val viewModel : MovieFavViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = MovieFavFragment()
    }
}