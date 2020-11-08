package com.oratakashi.jetpackacademy.ui.tv

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
import com.oratakashi.jetpackacademy.data.DataTv
import com.oratakashi.jetpackacademy.ui.main.MainInterface
import com.oratakashi.jetpackacademy.ui.tv.detail.DetailTvActivity
import kotlinx.android.synthetic.main.fragment_movie.bottom_sheet
import kotlinx.android.synthetic.main.fragment_tv.*


class TvFragment : Fragment(), MainInterface.Fragment, TvInterface {

    private val data: MutableList<DataTv> by lazy {
        ArrayList<DataTv>()
    }

    private val adapter: TvAdapter by lazy {
        TvAdapter(data, this)
    }

    private val viewMovie: TvViewModel by lazy {
        ViewModelProviders.of(this).get(TvViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        parent.registerFragment(this)

        rvTv.also {
            it.adapter = adapter
            it.layoutManager = GridLayoutManager(
                requireContext(),
                2
            )
        }

        data.addAll(viewMovie.getData())
        adapter.notifyItemInserted(0)
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
}