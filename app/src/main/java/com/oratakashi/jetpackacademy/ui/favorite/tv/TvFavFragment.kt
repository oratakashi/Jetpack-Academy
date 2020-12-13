package com.oratakashi.jetpackacademy.ui.favorite.tv

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
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.ui.tv.TvAdapter
import com.oratakashi.jetpackacademy.ui.tv.TvInterface
import com.oratakashi.jetpackacademy.ui.tv.detail.DetailTvActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tv_fav.*

@AndroidEntryPoint
class TvFavFragment : Fragment(), TvInterface {

    private val adapter : TvAdapter by lazy {
        TvAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFavTv.also {
            it.adapter = adapter
            it.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }

        viewModel.data.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.getTv()
        viewModel.setupSearch(etSearch)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv_fav, container, false)
    }

    override fun onClickMenu(data: DataTv) {
        startActivity(
            Intent(requireContext(), DetailTvActivity::class.java).also {
                it.putExtra("data", data)
            }, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
        )
    }

    private val viewModel : TvFavViewModel by viewModels()

    companion object {
        @JvmStatic
        fun newInstance() = TvFavFragment()
    }
}