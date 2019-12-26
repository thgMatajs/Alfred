package com.gentalhacode.data.product.source

import com.gentalhacode.data.product.repository.ProductDataSource
import com.gentalhacode.data.product.repository.ProductFirebase
import com.gentalhacode.model.entities.IProduct
import io.reactivex.Completable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ProductFirebaseDataSource(
    private val firebase: ProductFirebase
) : ProductDataSource.Firebase {

    override fun add(product: IProduct): Completable {
        return firebase.add(product)
    }

    override fun delete(product: IProduct): Completable {
        return delete(product)
    }

    override fun get(id: String): Single<IProduct> {
        return firebase.get(id)
    }
}