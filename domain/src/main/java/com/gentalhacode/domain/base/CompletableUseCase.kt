package com.gentalhacode.domain.base

import com.gentalhacode.domain.executor.PostExecutorThread
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
abstract class CompletableUseCase<in Params> constructor(
    private val postExecutorThread: PostExecutorThread
): BaseUseCase() {

    abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    fun execute(params: Params?,
                onComplete: () -> Unit,
                onError: (error: Throwable) -> Unit) {
        val completable = this.buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutorThread.scheduler)
        addDisposable(completable.subscribe(
            {
                onComplete.invoke()
            },
            { error ->
                onError.invoke(error)
            }
        ))
    }
}