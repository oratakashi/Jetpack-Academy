package com.oratakashi.jetpackacademy.ui.main

interface MainInterface {
    interface Fragment {
        fun setExpanded()
    }

    interface Activity {
        fun registerFragment(fragment: Fragment)
    }
}