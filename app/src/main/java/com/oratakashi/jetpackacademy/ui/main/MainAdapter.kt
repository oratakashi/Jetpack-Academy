package com.oratakashi.jetpackacademy.ui.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oratakashi.jetpackacademy.ui.movie.MovieFragment
import com.oratakashi.jetpackacademy.ui.tv.TvFragment

class MainAdapter(
    fragmentActivity: FragmentActivity,
    val parent: MainInterface.Activity
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> MovieFragment.newInstance(parent)
        else -> TvFragment.newInstance(parent)
    }
}