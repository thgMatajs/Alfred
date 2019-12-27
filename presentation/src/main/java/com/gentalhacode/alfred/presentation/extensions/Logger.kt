package com.gentalhacode.alfred.presentation.extensions

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
fun loggerL() = println("THG_LOG:: ..LOADING..")
fun loggerS(data: String = "") = println("THG_LOG:: SUCCESS --> $data")
fun loggerE(error: String) = println("THG_LOG:: ERROR --> $error")