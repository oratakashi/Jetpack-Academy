package com.oratakashi.jetpackacademy.ui.tv

import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.ui.main.MainInterface
import com.oratakashi.jetpackacademy.ui.tv.detail.DetailTvActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.bottom_sheet
import kotlinx.android.synthetic.main.fragment_tv.*
import javax.inject.Inject

@AndroidEntryPoint
class TvFragment : Fragment(), MainInterface.Fragment, TvInterface {

    private val data : MutableList<DataTv> by lazy {
        ArrayList<DataTv>()
    }

    private val adapter : TvAdapter by lazy {
        TvAdapter(data, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        parent.registerFragment(this)

        rvTv.also {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.adapter = adapter
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it){
                is TvState.Loading  -> {
                    shLoading.visibility = View.VISIBLE
                    rvTv.visibility = View.GONE
                    shLoading.startShimmerAnimation()
                }
                is TvState.Result   -> {
                    shLoading.stopShimmerAnimation()
                    shLoading.visibility = View.GONE
                    rvTv.visibility = View.VISIBLE

                    it.data.data.let {tv ->
                        if(tv != null){
                            data.clear()
                            data.addAll(tv)
                            adapter.notifyDataSetChanged()
                        }
                    }
                }
                is TvState.Error    -> {
                    shLoading.stopShimmerAnimation()
                    shLoading.visibility = View.GONE
                    rvTv.visibility = View.VISIBLE

                    dialog.setMessage(it.error.message)
                        .create()
                        .show()
                }
            }
        })

        viewModel.setupSearch(etSearch)
        viewModel.getTv()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    companion object {
        lateinit var parent: MainInterface.Activity

        @JvmStatic
        fun newInstance(callback: MainInterface.Activity) =
            TvFragment().apply {
                parent = callback
            }
    }

    override fun setExpanded() {
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onClickMenu(data: DataTv) {
        startActivity(
            Intent(requireContext(), DetailTvActivity::class.java).also {
                it.putExtra("data", data)
            }, ActivityOptions.makeSceneTransitionAnimation(requireActivity()).toBundle()
        )
    }

    private val viewModel : TvViewModel by viewModels()
    @Inject lateinit var dialog : AlertDialog.Builder
}