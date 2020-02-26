package com.gentalhacode.alfred.presentation.extensions

import com.crashlytics.android.Crashlytics
import com.gentalhacode.domain.exceptions.Exceptions

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
fun loggerL() = println("THG_LOG:: ..LOADING..")
fun loggerS(data: String = "") = println("THG_LOG:: SUCCESS --> $data")
fun loggerD(data: String = "") = println("THG_LOG:: DEBUG --> $data")
fun loggerE(error: String)  {
    Crashlytics.log(error)
    println("THG_LOG:: ERROR --> $error")
}
fun Throwable?.sendCrashlytics() = Crashlytics.logException(this ?: Exceptions.paramsIsNull)