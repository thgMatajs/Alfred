package com.gentalhacode.alfred.presentation.state

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
class ViewState<out T>(
    private val status: Status,
    val data: T? = null,
    private val exception: Throwable? = null
) {

    fun handle(
        onLoading: () -> Unit,
        onSuccess: (T?) -> Unit,
        onFailure: (Throwable?) -> Unit
    ) {
        when (status) {
            Status.LOADING -> onLoading.invoke()
            Status.SUCCESS -> onSuccess.invoke(data)
            Status.ERROR -> onFailure.invoke(exception)
        }
    }
}