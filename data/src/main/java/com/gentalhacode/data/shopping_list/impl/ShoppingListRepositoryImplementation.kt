package com.gentalhacode.data.shopping_list.impl

import com.gentalhacode.data.shopping_list.source.ShoppingListDataFactory
import com.gentalhacode.domain.repository.ShoppingListRepository
import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ShoppingListRepositoryImplementation(
    private val shoppingListDataFactory: ShoppingListDataFactory
) : ShoppingListRepository {

    override fun create(shoppingList: IGrocery): Completable {
        return shoppingListDataFactory.getFirebase().create(shoppingList)
    }

    override fun get(id: String): Single<IGrocery> {
        return shoppingListDataFactory.getFirebase().get(id)
    }

    override fun getAll(): Flowable<List<IGrocery>> {
        return shoppingListDataFactory.getFirebase().getAll()
    }

    override fun update(shoppingList: IGrocery): Completable {
        return shoppingListDataFactory.getFirebase().update(shoppingList)
    }

    override fun delete(shoppingList: IGrocery): Completable {
        return shoppingListDataFactory.getFirebase().delete(shoppingList)
    }
}