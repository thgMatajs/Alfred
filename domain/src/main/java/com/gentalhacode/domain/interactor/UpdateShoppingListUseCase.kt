package com.gentalhacode.domain.interactor

import com.gentalhacode.domain.base.CompletableUseCase
import com.gentalhacode.domain.exceptions.Exceptions
import com.gentalhacode.domain.executor.PostExecutorThread
import com.gentalhacode.domain.repository.ShoppingListRepository
import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Completable

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
class UpdateShoppingListUseCase(
    private val repository: ShoppingListRepository,
    postExecutorThread: PostExecutorThread
) : CompletableUseCase<IGrocery>(postExecutorThread) {
    override fun buildUseCaseCompletable(params: IGrocery?): Completable {
        return if (params != null) {
            repository.update(params)
        } else {
            Completable.error(Exceptions.paramsIsNull)
        }
    }
}