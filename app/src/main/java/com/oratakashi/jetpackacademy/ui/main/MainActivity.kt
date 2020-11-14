package com.oratakashi.jetpackacademy.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.fxn.BubbleTabBar
import com.fxn.OnBubbleClickListener
import com.oratakashi.jetpackacademy.R
import com.oratakashi.jetpackacademy.utils.ImageHelper
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), MainInterface.Activity {

    private val adapter: MainAdapter by lazy {
        MainAdapter(this, this)
    }

    private val child: MutableList<MainInterface.Fragment> by lazy {
        ArrayList<MainInterface.Fragment>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        ImageHelper.getPicasso(
            ivBackground,
            "https://benderranews.com/wp-content/uploads/2018/08/400x590-B2G3-OVO.jpg"
        )
        reduceDragSensitivity()
        vpMain.adapter = adapter
        vpMain.offscreenPageLimit = 2
        bnMenu.setupViewPager(vpMain)
        bnMenu.addBubbleListener(object : OnBubbleClickListener {
            override fun onBubbleClick(id: Int) {
                when (id) {
                    R.id.navigation_movie -> vpMain.setCurrentItem(0, true)
                    R.id.navigation_tv -> vpMain.setCurrentItem(1, true)
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
                if(child.isNotEmpty()){
                    child[position].setExpanded()
                }
            }
        })
    }

    private fun reduceDragSensitivity() {
        try {
            val ff =
                ViewPager2::class.java.getDeclaredField("mRecyclerView")
            ff.isAccessible = true
            val recyclerView = ff[vpMain] as RecyclerView
            val touchSlopField =
                RecyclerView::class.java.getDeclaredField("mTouchSlop")
            touchSlopField.isAccessible = true
            val touchSlop = touchSlopField[recyclerView] as Int
            touchSlopField[recyclerView] = touchSlop * 4
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun registerFragment(fragment: MainInterface.Fragment) {
        child.add(fragment)
    }
}