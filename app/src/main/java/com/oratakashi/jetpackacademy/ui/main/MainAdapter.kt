package com.oratakashi.jetpackacademy.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oratakashi.jetpackacademy.ui.favorite.FavoriteFragment
import com.oratakashi.jetpackacademy.ui.movie.MovieFragment
import com.oratakashi.jetpackacademy.ui.tv.TvFragment

class MainAdapter(
    fragmentActivity: FragmentActivity,
    val parent: MainInterface.Activity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0       -> MovieFragment.newInstance(parent)
        1       -> TvFragment.newInstance(parent)
        else    -> FavoriteFragment.newInstance(parent)
    }
}