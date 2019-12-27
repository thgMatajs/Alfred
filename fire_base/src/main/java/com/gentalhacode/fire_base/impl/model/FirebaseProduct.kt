package com.gentalhacode.fire_base.impl.model

import com.gentalhacode.model.entities.IProduct

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class FirebaseProduct(
    override val id: String = "",
    override val name: String = "",
    override val image: String = "",
    override val barcode: String = "",
    override val price: String = "",
    override val amount: String = "",
    override val isInTheCart: Boolean = false
): IProduct