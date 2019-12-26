package com.gentalhacode.domain.interactor.shopping_list

import com.gentalhacode.domain.base.CompletableUseCase
import com.gentalhacode.domain.exceptions.Exceptions
import com.gentalhacode.domain.executor.PostExecutorThread
import com.gentalhacode.domain.repository.ShoppingListRepository
import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Completable

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
class CreateShoppingListUseCase(
    private val repository: ShoppingListRepository,
    postExecutorThread: PostExecutorThread
): CompletableUseCase<IGrocery>(postExecutorThread) {
    override fun buildUseCaseCompletable(params: IGrocery?): Completable {
        return if (params != null) {
            repository.create(params)
        } else {
            Completable.error(Exceptions.paramsIsNull)
        }
    }
}