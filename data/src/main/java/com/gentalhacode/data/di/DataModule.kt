package com.gentalhacode.data.di

import com.gentalhacode.data.product.impl.ProductRepositoryImplementation
import com.gentalhacode.data.product.source.ProductDataFactory
import com.gentalhacode.data.product.source.ProductFirebaseDataSource
import com.gentalhacode.data.shopping_list.impl.ShoppingListRepositoryImplementation
import com.gentalhacode.data.shopping_list.source.ShoppingListDataFactory
import com.gentalhacode.data.shopping_list.source.ShoppingListFirebaseDataSource
import com.gentalhacode.domain.repository.ProductRepository
import com.gentalhacode.domain.repository.ShoppingListRepository
import org.koin.dsl.module

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
val dataModule = module {
    factory<ProductRepository> { ProductRepositoryImplementation(get()) }
    factory { ProductDataFactory(get()) }
    factory { ProductFirebaseDataSource(get()) }

    factory<ShoppingListRepository> { ShoppingListRepositoryImplementation(get()) }
    factory { ShoppingListDataFactory(get()) }
    factory { ShoppingListFirebaseDataSource(get()) }
}