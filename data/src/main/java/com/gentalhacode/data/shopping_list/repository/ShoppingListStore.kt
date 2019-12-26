package com.gentalhacode.data.shopping_list.repository

import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
interface ShoppingListStore {
    fun create(shoppingList: IGrocery): Completable
    fun get(id: String): Single<IGrocery>
    fun getAll(): Flowable<List<IGrocery>>
    fun update(shoppingList: IGrocery): Completable
    fun delete(shoppingList: IGrocery): Completable
}