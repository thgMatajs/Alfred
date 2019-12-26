package com.gentalhacode.data.product.source

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ProductDataFactory(
    private val productFirebaseDataSource: ProductFirebaseDataSource
) {
    fun getFirebase() = productFirebaseDataSource
}