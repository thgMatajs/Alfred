package com.gentalhacode.model.entities

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface IProduct {
    val id: String
    val name: String
    val image: String
    val barcode: String
    val price: String
    val amount: String
    val brand: String
    var isInTheCart: Boolean
}