package com.gentalhacode.domain.executor

import io.reactivex.Scheduler

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface PostExecutorThread {
    val scheduler: Scheduler
}