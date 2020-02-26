package com.gentalhacode.model.entities

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface IGrocery {
    var id: String
    val name: String
    val products: List<IProduct>
    val emailUsers: MutableList<String>
    var active: Boolean
}