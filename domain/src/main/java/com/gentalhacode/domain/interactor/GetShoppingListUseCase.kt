package com.gentalhacode.domain.interactor

import com.gentalhacode.domain.base.SingleUseCase
import com.gentalhacode.domain.exceptions.Exceptions
import com.gentalhacode.domain.executor.PostExecutorThread
import com.gentalhacode.domain.repository.ShoppingListRepository
import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
class GetShoppingListUseCase(
    private val repository: ShoppingListRepository,
    postExecutorThread: PostExecutorThread
) : SingleUseCase<IGrocery, String>(postExecutorThread) {

    override fun buildUseCaseSingle(params: String?): Single<IGrocery> {
        return if (!params.isNullOrBlank()) {
            repository.get(params)
        } else {
            Single.error(Exceptions.paramsIsNullOrBlank)
        }
    }
}