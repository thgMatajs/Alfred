package com.gentalhacode.fire_base.impl.model

import com.gentalhacode.model.entities.IGrocery

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class FirebaseGrocery(
    override var id: String = "",
    override val products: List<FirebaseProduct> = emptyList(),
    override val emailUsers: List<String> = emptyList(),
    override val isActive: Boolean = false
): IGrocery