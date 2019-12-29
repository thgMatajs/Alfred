package com.gentalhacode.alfred.model

import com.gentalhacode.model.entities.IUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class ViewUser(
    override val id: String,
    override val name: String,
    override val email: String,
    override val image: String,
    override val nickName: String
//    override val homes: List<IHome>,
//    override val groceryShopping: List<IGrocery>

) : IUser {

    companion object {
        private val fbUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        fun getUser() = ViewUser(
            id = fbUser?.uid ?: "",
            name = fbUser?.displayName ?: "",
            email = fbUser?.email ?: "",
            image = fbUser?.photoUrl.toString(),
            nickName = fbUser?.displayName ?: ""
        )
    }
}