package com.gentalhacode.alfred.presentation.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.OvershootInterpolator

/**
 * .:.:.:. Created by @thgMatajs on 20/12/19 .:.:.:.
 */
fun View.setGone() {
    this.visibility = View.GONE
}
fun View.setVisible() {
    this.visibility = View.VISIBLE
}
fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}
fun View.animateContentIn() {
    val scaleX = ObjectAnimator.ofFloat(this, View.SCALE_X, 0f, 1f)
    val scaleY = ObjectAnimator.ofFloat(this, View.SCALE_Y, 0f, 1f)
    val animatorSet = AnimatorSet().apply {
        interpolator = OvershootInterpolator()
        duration = 1000
        playTogether(scaleX, scaleY)
    }
    this.setVisible()
    animatorSet.start()
}