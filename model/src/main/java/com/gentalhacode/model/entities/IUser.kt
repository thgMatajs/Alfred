package com.gentalhacode.model.entities

import com.gentalhacode.model.entities.IHome

/**
 * .:.:.:. Created by @thgMatajs on 23/12/19 .:.:.:.
 */
interface IUser {
    val id: String
    val name: String
    val email: String
    val image: String
    val nickName: String
    val homes: List<IHome>
}