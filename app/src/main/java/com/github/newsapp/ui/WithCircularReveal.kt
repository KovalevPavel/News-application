package com.github.newsapp.ui

import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import androidx.viewbinding.ViewBinding
import kotlin.math.hypot

interface WithCircularReveal {
    fun reveal(binder: ViewBinding) {
        val view = binder.root
//        view.visibility = View.INVISIBLE
        // Check if the runtime version is at least Lollipop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the center for the clipping circle
            val cx = view.width / 2
            val cy = view.height / 2

            // get the final radius for the clipping circle
            val finalRadius = hypot(cx.toDouble(), cy.toDouble()).toFloat()

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0f, finalRadius)
            // make the view visible and start the animation
            anim.duration = 1500
            view.visibility = View.VISIBLE
            anim.start()
        } else {
            // set the view to invisible without a circular reveal animation below Lollipop
            view.visibility = View.INVISIBLE
        }
    }
}