package com.gentalhacode.model.entities

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface IHome {
    val id: String
    val users: List<IUser>
    val groceryShopping: List<IGrocery>
}