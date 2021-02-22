package com.oratakashi.jetpackacademy.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.fxn.BubbleTabBar
import com.fxn.OnBubbleClickListener
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.ui.main.MainInterface
import com.oratakashi.jetpackacademy.ui.movie.MovieFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite.*

@AndroidEntryPoint
class FavoriteFragment : Fragment(), MainInterface.Fragment {

    private val adapter : FavoriteAdapter by lazy {
        FavoriteAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        bottom_sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT

        parent.registerFragment(this)

        vpFav.also {
            it.adapter = adapter
            it.isUserInputEnabled = false
            it.offscreenPageLimit = 2
        }

        bnSubMenu.setupViewPager(vpFav)
        bnSubMenu.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when(id){
                    R.id.navigation_fav_movie   -> vpFav.setCurrentItem(0, true)
                    R.id.navigation_fav_tv      -> vpFav.setCurrentItem(1, true)
                }
            }
        })
    }

    private fun BubbleTabBar.setupViewPager(viewPager: ViewPager2) {
        val tabs = this
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabs.setSelected(position)
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun setExpanded() {
        BottomSheetBehavior.from(bottom_sheet).state = BottomSheetBehavior.STATE_EXPANDED
        bottom_sheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    }

    companion object {
        lateinit var parent: MainInterface.Activity
        @JvmStatic
        fun newInstance(callback: MainInterface.Activity) =
            FavoriteFragment().apply {
                parent = callback
            }
    }
}