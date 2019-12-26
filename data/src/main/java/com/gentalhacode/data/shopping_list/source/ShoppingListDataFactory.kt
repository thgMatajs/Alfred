package com.gentalhacode.data.shopping_list.source

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ShoppingListDataFactory(
    private val shoppingListFirebaseDataSource: ShoppingListFirebaseDataSource
) {
    fun getFirebase() = shoppingListFirebaseDataSource
}