package com.gentalhacode.alfred.presentation.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gentalhacode.alfred.presentation.extensions.setFailure
import com.gentalhacode.alfred.presentation.extensions.setLoading
import com.gentalhacode.alfred.presentation.extensions.setSuccess
import com.gentalhacode.alfred.presentation.state.ViewState
import com.gentalhacode.domain.interactor.shopping_list.DeleteShoppingListUseCase
import com.gentalhacode.model.entities.IGrocery

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
class DeleteShoppingListViewModel(
    private val deleteUseCase: DeleteShoppingListUseCase
) : ViewModel() {

    private val deleteLiveData: MutableLiveData<ViewState<Nothing>> = MutableLiveData()
    fun observeDeleteLiveData(): LiveData<ViewState<Nothing>> = deleteLiveData

    fun delete(shoppingList: IGrocery) {
        deleteLiveData.setLoading()
        deleteUseCase.execute(shoppingList,
            onComplete = { deleteLiveData.setSuccess() },
            onError = { deleteLiveData.setFailure(it) })
    }

    override fun onCleared() {
        super.onCleared()
        deleteUseCase.dispose()
    }
}