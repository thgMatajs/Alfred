package com.gentalhacode.domain.base

import com.gentalhacode.domain.executor.PostExecutorThread
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
abstract class FlowableUseCase<T, in Params> constructor(
    private val postExecutorThread: PostExecutorThread
) {
    private val disables = CompositeDisposable()
    abstract fun buildUseCaseFlowable(params: Params? = null): Flowable<T>

    fun execute(
        params: Params? = null,
        onNext: (T) -> Unit,
        onError: (e: Throwable) -> Unit,
        onComplete: (() -> Unit)? = null
    ) {
        val flowable = this.buildUseCaseFlowable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutorThread.scheduler)
        addDisbosable(
            flowable.subscribe(
                { t: T ->
                    onNext.invoke(t)
                },
                { error ->
                    onError.invoke(error)
                },
                {
                    onComplete?.invoke()
                })
        )
    }

    private fun addDisbosable(disposable: Disposable) {
        disables.add(disposable)
    }

    fun dispose() {
        disables.clear()
    }
}