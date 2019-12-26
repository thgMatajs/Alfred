package com.gentalhacode.domain.repository

import com.gentalhacode.model.entities.IProduct
import io.reactivex.Completable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
interface ProductRepository {
    fun add(product: IProduct): Completable
    fun delete(product: IProduct): Completable
    fun get(id: String): Single<IProduct>
}