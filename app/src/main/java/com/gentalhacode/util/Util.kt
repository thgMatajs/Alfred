package com.gentalhacode.util

import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
fun randomUuid() = UUID.randomUUID().toString()

fun EditText.value(): String = this.text.toString()

fun RecyclerView.onScrollListener(onScrolled: (Int, Int) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(dx, dy)
        }
    })
}