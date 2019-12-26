package com.gentalhacode.domain.interactor.product

import com.gentalhacode.domain.base.CompletableUseCase
import com.gentalhacode.domain.exceptions.Exceptions
import com.gentalhacode.domain.executor.PostExecutorThread
import com.gentalhacode.domain.repository.ProductRepository
import com.gentalhacode.model.entities.IProduct
import io.reactivex.Completable

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class DeleteProductUseCase(
    private val repository: ProductRepository,
    postExecutorThread: PostExecutorThread
) : CompletableUseCase<IProduct>(postExecutorThread) {

    override fun buildUseCaseCompletable(params: IProduct?): Completable {
        return if (params != null) {
            repository.delete(params)
        } else {
            Completable.error(Exceptions.paramsIsNull)
        }
    }
}