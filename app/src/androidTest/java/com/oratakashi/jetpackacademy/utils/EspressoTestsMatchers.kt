package com.oratakashi.jetpackacademy.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

class EspressoTestsMatchers {
    fun withDrawable(resourceId: Int): Matcher<View?>? {
        return DrawableMatcher(resourceId)
    }

    fun noDrawable(): Matcher<View?>? {
        return DrawableMatcher(-1)
    }

    class DrawableMatcher(private val expectedId: Int) :
        TypeSafeMatcher<View>(View::class.java) {
        private var resourceName: String? = null
        override fun matchesSafely(item: View): Boolean {
            if (item !is ImageView) {
                return false
            }
            val imageView = item
            if (expectedId < 0) {
                return imageView.drawable == null
            }
            val resources = item.getContext().resources
            val expectedDrawable =
                resources.getDrawable(expectedId)
            resourceName = resources.getResourceEntryName(expectedId)
            if (expectedDrawable == null) {
                return false
            }
            val bitmap = getBitmap(imageView.drawable)
            val otherBitmap = getBitmap(expectedDrawable)
            return bitmap.sameAs(otherBitmap)
        }

        private fun getBitmap(drawable: Drawable): Bitmap {
            val bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
            return bitmap
        }

        override fun describeTo(description: Description) {
            description.appendText("with drawable from resource id: ")
            description.appendValue(expectedId)
        }

    }
}