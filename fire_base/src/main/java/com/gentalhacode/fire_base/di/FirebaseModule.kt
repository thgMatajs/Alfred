package com.gentalhacode.fire_base.di

import com.gentalhacode.data.shopping_list.repository.ShoppingListFirebase
import com.gentalhacode.fire_base.impl.shopping_list.ShoppingListFirebaseImplementation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.dsl.module

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
val firebaseModule = module {
    factory { FirebaseAuth.getInstance() }
    factory { FirebaseFirestore.getInstance() }
    factory<ShoppingListFirebase> { ShoppingListFirebaseImplementation(get(), get()) }
}