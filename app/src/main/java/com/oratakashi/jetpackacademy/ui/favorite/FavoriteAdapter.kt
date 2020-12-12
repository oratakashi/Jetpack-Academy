package com.oratakashi.jetpackacademy.ui.favorite

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oratakashi.jetpackacademy.ui.favorite.movie.MovieFavFragment
import com.oratakashi.jetpackacademy.ui.favorite.tv.TvFavFragment

class FavoriteAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment = when (position){
        0       -> MovieFavFragment.newInstance()
        else    -> TvFavFragment.newInstance()
    }
}