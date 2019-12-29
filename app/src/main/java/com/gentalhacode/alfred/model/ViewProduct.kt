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
    override var isInTheCart: Boolean,
    override val brand: String
) : IProduct

fun IProduct.toView() = ViewProduct(
    id = id,
    name = name,
    image = image,
    barcode = barcode,
    price = price,
    amount = amount,
    isInTheCart = isInTheCart,
    brand = brand
)