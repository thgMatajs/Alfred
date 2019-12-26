package com.gentalhacode.domain.di

import com.gentalhacode.domain.interactor.product.AddProductUseCase
import com.gentalhacode.domain.interactor.product.DeleteProductUseCase
import com.gentalhacode.domain.interactor.product.GetProductUseCase
import com.gentalhacode.domain.interactor.shopping_list.*
import org.koin.dsl.module

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
val domainModule = module {
    factory { CreateShoppingListUseCase(get(), get()) }
    factory { DeleteShoppingListUseCase(get(), get()) }
    factory { GetAllShoppingListUseCase(get(), get()) }
    factory { GetShoppingListUseCase(get(), get()) }
    factory { UpdateShoppingListUseCase(get(), get()) }

    factory { AddProductUseCase(get(), get()) }
    factory { DeleteProductUseCase(get(), get()) }
    factory { GetProductUseCase(get(), get()) }
}