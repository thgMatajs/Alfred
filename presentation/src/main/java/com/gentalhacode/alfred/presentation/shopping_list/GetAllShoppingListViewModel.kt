package com.gentalhacode.alfred.presentation.shopping_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gentalhacode.alfred.presentation.extensions.setFailure
import com.gentalhacode.alfred.presentation.extensions.setLoading
import com.gentalhacode.alfred.presentation.extensions.setSuccess
import com.gentalhacode.alfred.presentation.state.ViewState
import com.gentalhacode.domain.interactor.shopping_list.GetAllShoppingListUseCase
import com.gentalhacode.model.entities.IGrocery

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
class GetAllShoppingListViewModel(
    private val useCase: GetAllShoppingListUseCase
) : ViewModel() {
    private val getAllLiveData: MutableLiveData<ViewState<List<IGrocery>>> = MutableLiveData()
    fun observeGetAllLiveData(): LiveData<ViewState<List<IGrocery>>> = getAllLiveData

    init {
        getAll()
    }

    private fun getAll() {
        getAllLiveData.setLoading()
        useCase.execute(
            onNext = { allShoppingLists ->
                getAllLiveData.setSuccess(allShoppingLists)
            },
            onError = { error ->
                getAllLiveData.setFailure(error)
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        useCase.dispose()
    }
}