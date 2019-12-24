package com.gentalhacode.domain.interactor

import com.gentalhacode.domain.base.FlowableUseCase
import com.gentalhacode.domain.executor.PostExecutorThread
import com.gentalhacode.domain.repository.ShoppingListRepository
import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Flowable

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
class GetAllShoppingListUseCase(
    private val repository: ShoppingListRepository,
    postExecutorThread: PostExecutorThread
) : FlowableUseCase<List<IGrocery>, Nothing>(postExecutorThread) {
    override fun buildUseCaseFlowable(params: Nothing?): Flowable<List<IGrocery>> {
        return repository.getAll()
    }
}