package com.gentalhacode.data.shopping_list.source

import com.gentalhacode.data.shopping_list.repository.ShoppingListDataSource
import com.gentalhacode.data.shopping_list.repository.ShoppingListFirebase
import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ShoppingListFirebaseDataSource(
    private val firebase: ShoppingListFirebase
) : ShoppingListDataSource.Firebase {

    override fun create(shoppingList: IGrocery): Completable {
        return firebase.create(shoppingList)
    }

    override fun get(id: String): Single<IGrocery> {
        return firebase.get(id)
    }

    override fun getAll(): Flowable<List<IGrocery>> {
        return firebase.getAll()
    }

    override fun update(shoppingList: IGrocery): Completable {
        return firebase.update(shoppingList)
    }

    override fun delete(shoppingList: IGrocery): Completable {
        return firebase.delete(shoppingList)
    }
}