package com.gentalhacode.alfred.model

import com.gentalhacode.model.entities.IProduct

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class ViewProduct(
    override val id: String,
    override val name: String,
    override val image: String,
    override val barcode: String,
    override val price: String,
    override val amount: String,
    override val isInTheCart: Boolean
) : IProduct