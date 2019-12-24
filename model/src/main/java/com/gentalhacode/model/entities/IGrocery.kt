package com.gentalhacode.model.entities

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface IGrocery {
    val id: String
    val products: List<IProduct>
    val users: List<IUser>
    val isActive: Boolean
}