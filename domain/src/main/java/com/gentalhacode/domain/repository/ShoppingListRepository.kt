package com.gentalhacode.domain.repository

import com.gentalhacode.model.entities.IGrocery
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface ShoppingListRepository {

    fun create(shoppingList: IGrocery): Completable
    fun get(id: String): Single<IGrocery>
    fun getAll(): Flowable<List<IGrocery>>
    fun update(shoppingList: IGrocery): Completable
    fun delete(shoppingList: IGrocery): Completable
}