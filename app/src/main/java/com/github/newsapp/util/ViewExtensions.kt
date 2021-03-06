package com.github.newsapp.util

import android.view.View
import android.view.ViewAnimationUtils
import kotlin.math.hypot

/**
 * Starts circular reveal animation
 */
fun View.startCircularReveal() {
    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            v: View?,
            left: Int,
            top: Int,
            right: Int,
            bottom: Int,
            oldLeft: Int,
            oldTop: Int,
            oldRight: Int,
            oldBottom: Int
        ) {
            v?.removeOnLayoutChangeListener(this)
            val cx = width / 2
            val cy = height / 2
            val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()
            val anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, finalRadius)
            anim.duration = 1000L
            v?.visibility = View.VISIBLE
            anim.start()
        }
    }
    )

//    addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
//        override fun onLayoutChange(
//            v: View, left: Int, top: Int, right: Int, bottom: Int, oldLeft: Int, oldTop: Int,
//            oldRight: Int, oldBottom: Int
//        ) {
//            v.removeOnLayoutChangeListener(this)
//            val cx = (v.left + v.right)/2
//            val cy = v.bottom
//            val radius = hypot(right.toDouble(), bottom.toDouble()).toInt()
//            ViewAnimationUtils.createCircularReveal(v, cx, cy, 0f, radius.toFloat()).apply {
//                interpolator = DecelerateInterpolator(2f)
//                duration = 1000
//                start()
//            }
//        }
//    })
}