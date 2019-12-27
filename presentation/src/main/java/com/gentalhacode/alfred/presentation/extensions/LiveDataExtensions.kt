package com.gentalhacode.alfred.presentation.extensions

import androidx.lifecycle.MutableLiveData
import com.gentalhacode.alfred.presentation.state.Status
import com.gentalhacode.alfred.presentation.state.ViewState

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
fun <T> MutableLiveData<ViewState<T>>.setLoading() {
    postValue(ViewState(Status.LOADING))
}

fun <T> MutableLiveData<ViewState<T>>.setSuccess(data: T? = null) {
    postValue(ViewState(Status.SUCCESS, data = data))
}

fun <T> MutableLiveData<ViewState<T>>.setFailure(error: Throwable) {
    postValue(ViewState(Status.ERROR, exception = error))
}