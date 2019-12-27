package com.gentalhacode.alfred.presentation.di

import com.gentalhacode.alfred.presentation.execution.UiThread
import com.gentalhacode.alfred.presentation.shopping_list.CreateShoppingListViewModel
import com.gentalhacode.domain.executor.PostExecutorThread
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
val presentationModule = module {
    factory<PostExecutorThread> { UiThread() }
    viewModel { CreateShoppingListViewModel(get()) }
}