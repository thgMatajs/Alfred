package com.gentalhacode.alfred.model

import com.gentalhacode.model.entities.IGrocery
import com.gentalhacode.model.entities.IProduct
import com.gentalhacode.model.entities.IUser

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class ViewGrocery(
    override var id: String,
    override val products: List<IProduct>,
    override val users: List<IUser>,
    override val isActive: Boolean
) : IGrocery