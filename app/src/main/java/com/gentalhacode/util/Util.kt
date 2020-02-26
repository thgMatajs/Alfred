package com.gentalhacode.util

import android.content.Context
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
fun randomUuid() = UUID.randomUUID().toString()

fun EditText.value(): String = this.text.toString()

fun EditText.isBlank(): Boolean {
    return if (this.value().isBlank()) {
        this.error = "Campo obrigatorio."
        true
    } else {
        false
    }
}

fun RecyclerView.onScrollListener(onScrolled: (Int, Int) -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            onScrolled(dx, dy)
        }
    })
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.toast(message: Int) {
    Toast.makeText(this, this.getString(message), Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: String) {
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(message: Int) {
    Toast.makeText(this.requireContext(), this.getString(message), Toast.LENGTH_SHORT).show()
}