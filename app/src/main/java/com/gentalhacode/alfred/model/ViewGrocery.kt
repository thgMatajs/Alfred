package com.gentalhacode.alfred.model

import com.gentalhacode.model.entities.IGrocery
import com.gentalhacode.model.entities.IProduct
import com.gentalhacode.model.entities.IUser

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class ViewGrocery(
    override var id: String,
    override var products: List<IProduct>,
    override val emailUsers: MutableList<String>,
    override val active: Boolean
) : IGrocery

fun IGrocery.toView() = ViewGrocery(
    id = id,
    products = products,
    emailUsers = emailUsers,
    active = active
)