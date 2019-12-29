package com.gentalhacode.fire_base.impl.shopping_list

import com.gentalhacode.data.shopping_list.repository.ShoppingListFirebase
import com.gentalhacode.fire_base.BuildConfig
import com.gentalhacode.fire_base.impl.model.FirebaseGrocery
import com.gentalhacode.fire_base.impl.model.FirebaseUser
import com.gentalhacode.model.entities.IGrocery
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.BackpressureStrategy
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

/**
 * .:.:.:. Created by @thgMatajs on 26/12/19 .:.:.:.
 */
class ShoppingListFirebaseImplementation(
    fbAuth: FirebaseAuth,
    fireStore: FirebaseFirestore
) : ShoppingListFirebase {

    private val storageRef = FirebaseStorage.getInstance().reference.child(SHOPPING_LIST_COLLECTION_NAME)
    private val currentUser = fbAuth.currentUser
    private val collection = fireStore.collection(BuildConfig.BASE_URL)

    override fun create(shoppingList: IGrocery): Completable {
        return Completable.create { emitter ->
            if (currentUser == null) {
                Completable.error(IllegalArgumentException("params cannot be null"))
            } else {
                val saveTask = if (shoppingList.id.isBlank()) {
                    collection.add(shoppingList)
                        .continueWith { task ->
                            val doc = task.result
                            shoppingList.id = doc?.id ?: UUID.randomUUID().toString()
                            doc?.update(
                                mapOf(
                                    ID_KEY to shoppingList.id
                                )
                            )
                        }
                } else {
                    collection.document(shoppingList.id)
                        .set(shoppingList, SetOptions.merge())
                }
                    .addOnSuccessListener { emitter.onComplete() }
                    .addOnFailureListener { e -> emitter.onError(e) }
            }
        }
    }

    override fun get(id: String): Single<IGrocery> {
        return Single.create { emitter ->
            collection.document(id)
                .addSnapshotListener { documentSnapshot, error ->
                    if (error == null) {
                        documentSnapshot?.toObject(FirebaseGrocery::class.java)?.let { emitter.onSuccess(it) }
                    } else {
                        emitter.onError(error)
                    }
                }
        }
    }

    override fun getAll(): Flowable<List<IGrocery>> {
        return Flowable.create({emitter ->
            collection.whereArrayContains(FieldPath.of(USERS_KEY), currentUser?.email as Any)
                .addSnapshotListener { result, error ->
                    if (error == null && result != null) {
                        val list = result.map { document ->
                            document.toObject(FirebaseGrocery::class.java)
                        }
                        emitter.onNext(list)
                    } else {
                        emitter.onError(error?.cause ?: IllegalArgumentException("Deu Merda"))
                    }
                }
        }, BackpressureStrategy.LATEST)
    }

    override fun update(shoppingList: IGrocery): Completable {
        return Completable.create {
            it.onComplete()
        }
    }

    override fun delete(shoppingList: IGrocery): Completable {
        return Completable.create { emitter ->
            collection.document(shoppingList.id)
                .delete()
                .addOnSuccessListener { emitter.onComplete() }
                .addOnFailureListener { emitter.onError(it) }
        }
    }

    companion object {
        const val SHOPPING_LIST_COLLECTION_NAME = "shopping_list"
        const val USERS_KEY = "users"
        const val ID_KEY = "id"
        const val COVER_URL_KEY = "coverUrl"
    }
}