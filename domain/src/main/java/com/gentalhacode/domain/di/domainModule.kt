package com.gentalhacode.domain.di

import com.gentalhacode.domain.interactor.*
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
}