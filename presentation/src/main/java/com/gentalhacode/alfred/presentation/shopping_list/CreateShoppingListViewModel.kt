package com.gentalhacode.alfred.presentation.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gentalhacode.alfred.presentation.extensions.setFailure
import com.gentalhacode.alfred.presentation.extensions.setLoading
import com.gentalhacode.alfred.presentation.extensions.setSuccess
import com.gentalhacode.alfred.presentation.state.ViewState
import com.gentalhacode.domain.interactor.shopping_list.CreateShoppingListUseCase
import com.gentalhacode.model.entities.IGrocery

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
class CreateShoppingListViewModel(
    private val createUseCase: CreateShoppingListUseCase
) : ViewModel() {

    private val createLiveData: MutableLiveData<ViewState<IGrocery>> = MutableLiveData()
    fun observeCreateLiveData(): LiveData<ViewState<IGrocery>> = createLiveData

    fun create(shoppingList: IGrocery) {
        createLiveData.setLoading()
        createUseCase.execute(shoppingList,
            onComplete = {
                createLiveData.setSuccess()
            },
            onError = { error ->
                createLiveData.setFailure(error)
            })
    }


    override fun onCleared() {
        super.onCleared()
        createUseCase.dispose()
    }
}