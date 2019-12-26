package com.gentalhacode.data.product.impl

import com.gentalhacode.data.product.source.ProductDataFactory
import com.gentalhacode.domain.repository.ProductRepository
import com.gentalhacode.model.entities.IProduct
import io.reactivex.Completable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ProductRepositoryImplementation(
    private val productDataFactory: ProductDataFactory
) : ProductRepository {

    override fun add(product: IProduct): Completable {
        return productDataFactory.getFirebase().add(product)
    }

    override fun delete(product: IProduct): Completable {
        return productDataFactory.getFirebase().delete(product)
    }

    override fun get(id: String): Single<IProduct> {
        return productDataFactory.getFirebase().get(id)
    }
}