package com.gentalhacode.domain.interactor.product

import com.gentalhacode.domain.base.SingleUseCase
import com.gentalhacode.domain.exceptions.Exceptions
import com.gentalhacode.domain.executor.PostExecutorThread
import com.gentalhacode.domain.repository.ProductRepository
import com.gentalhacode.model.entities.IProduct
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class GetProductUseCase(
    private val repository: ProductRepository,
    postExecutorThread: PostExecutorThread
) : SingleUseCase<IProduct, String>(postExecutorThread) {

    override fun buildUseCaseSingle(params: String?): Single<IProduct> {
        return if (!params.isNullOrBlank()) {
            repository.get(params)
        } else {
            Single.error(Exceptions.paramsIsNullOrBlank)
        }
    }
}