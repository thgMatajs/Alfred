package com.gentalhacode.domain.exceptions

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
object Exceptions {
    val paramsIsNull = IllegalArgumentException("params cannot be null")
    val paramsIsNullOrBlank = IllegalArgumentException("params cannot be null or blank")
}