package com.gentalhacode.alfred.presentation.execution

import com.gentalhacode.domain.executor.PostExecutorThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
class UiThread : PostExecutorThread {

    override val scheduler: Scheduler
        get() = AndroidSchedulers.mainThread()
}