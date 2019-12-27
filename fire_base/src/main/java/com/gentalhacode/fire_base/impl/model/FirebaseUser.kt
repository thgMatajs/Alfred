package com.gentalhacode.fire_base.impl.model

import com.gentalhacode.model.entities.IUser

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class FirebaseUser(
    override val id: String = "",
    override val name: String = "",
    override val email: String = "",
    override val image: String = "",
    override val nickName: String = ""
): IUser