package com.gentalhacode.alfred.presentation.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gentalhacode.alfred.presentation.extensions.setFailure
import com.gentalhacode.alfred.presentation.extensions.setLoading
import com.gentalhacode.alfred.presentation.extensions.setSuccess
import com.gentalhacode.alfred.presentation.state.ViewState
import com.gentalhacode.domain.interactor.shopping_list.GetShoppingListUseCase
import com.gentalhacode.model.entities.IGrocery

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
class GetShoppingListViewModel(
    private val usecase: GetShoppingListUseCase
) : ViewModel() {

    private val getShoppingListLiveData: MutableLiveData<ViewState<IGrocery>> = MutableLiveData()
    fun observeGetShoppingListLiveData(): LiveData<ViewState<IGrocery>> = getShoppingListLiveData

    fun get(id: String) {
        getShoppingListLiveData.setLoading()
        usecase.execute(
            params = id,
            onSuccess = { shoppingList ->
                getShoppingListLiveData.setSuccess(shoppingList)
            },
            onError = { error ->
                getShoppingListLiveData.setFailure(error)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        usecase.dispose()
    }
}