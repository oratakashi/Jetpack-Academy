package com.oratakashi.jetpackacademy.ui.tv

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
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.ui.main.MainInterface
import com.oratakashi.jetpackacademy.ui.tv.detail.DetailTvActivity
import com.oratakashi.jetpackacademy.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.bottom_sheet
import kotlinx.android.synthetic.main.fragment_tv.*
import javax.inject.Inject

@AndroidEntryPoint
class TvFragment : Fragment(), MainInterface.Fragment, TvInterface {

    private val adapter : TvAdapter by lazy {
        TvAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        parent.registerFragment(this)

        rvTv.also {
            it.layoutManager = GridLayoutManager(requireContext(), 2)
            it.adapter = adapter
            it.isNestedScrollingEnabled = false
        }

        viewModel.state.observe(viewLifecycleOwner, Observer {
            when(it){
                is TvState.Loading  -> {
                    shLoading.visibility = View.VISIBLE
                    rvTv.visibility = View.GONE
                    shLoading.startShimmerAnimation()
                    EspressoIdlingResource.increment()
                }
                is TvState.Result   -> {
                    if(shLoading.isVisible){
                        shLoading.stopShimmerAnimation()
                        shLoading.visibility = View.GONE
                    }
                    if(!rvTv.isVisible){
                        rvTv.visibility = View.VISIBLE
                    }
                    EspressoIdlingResource.decrement()
                }
                is TvState.Error    -> {
                    EspressoIdlingResource.decrement()
                    shLoading.visibility = View.GONE
                    shLoading.stopShimmerAnimation()
                    rvTv.visibility = View.VISIBLE

                    dialog.setMessage(it.error.message)
                        .create()
                        .show()
                }
            }
        })
        viewModel.data.observe(viewLifecycleOwner, Observer(adapter::submitList))

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
        bottom_sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
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