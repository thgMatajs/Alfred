package com.gentalhacode.util

import android.widget.EditText
import java.util.*

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
fun randomUuid() = UUID.randomUUID().toString()
fun EditText.value(): String = this.text.toString()