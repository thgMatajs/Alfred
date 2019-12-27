package com.gentalhacode.alfred.model

import com.gentalhacode.model.entities.IUser

/**
 * .:.:.:. Created by @thgMatajs on 27/12/19 .:.:.:.
 */
data class ViewUser(

    override val id: String,
    override val name: String,
    override val email: String,
    override val image: String,
    override val nickName: String
//    override val homes: List<IHome>,
//    override val groceryShopping: List<IGrocery>

) : IUser