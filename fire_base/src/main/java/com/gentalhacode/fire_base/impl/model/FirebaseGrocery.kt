package com.gentalhacode.fire_base.impl.model

import com.gentalhacode.model.entities.IGrocery

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class FirebaseGrocery(
    override var id: String = "",
    override var products: List<FirebaseProduct> = emptyList(),
    override val emailUsers: List<String> = emptyList(),
    override val active: Boolean = false
): IGrocery