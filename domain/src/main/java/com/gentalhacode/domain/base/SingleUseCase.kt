package com.gentalhacode.domain.base

import com.gentalhacode.domain.executor.PostExecutorThread
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
abstract class SingleUseCase<T, in Params> constructor(
    private val postExecutorThread: PostExecutorThread
) : BaseUseCase() {

    abstract fun buildUseCaseSingle(params: Params? = null): Single<T>
    fun execute(
        params: Params? = null,
        onSuccess: (T) -> Unit,
        onError: (error: Throwable) -> Unit
    ) {
        val single = this.buildUseCaseSingle(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutorThread.scheduler)
        addDisposable(
            single.subscribe(
                { t: T ->
                    onSuccess.invoke(t)
                },
                { error ->
                    onError.invoke(error)
                }
            )
        )
    }
}