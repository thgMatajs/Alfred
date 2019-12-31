package com.gentalhacode.util

import android.content.Context
import android.content.SharedPreferences

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
class SharedUtil(context: Context) {
    companion object {
        private const val SHARED_UTIL_PACKAGE_NAME = "com.gentalhacode.util"
        private const val CURRENT_SHOPPING_LIST_UUID = "current_shopping_list_uuid"
    }

    private val alfredSharedPref: SharedPreferences by lazy {
        context.getSharedPreferences(SHARED_UTIL_PACKAGE_NAME, Context.MODE_PRIVATE)
    }

    fun clear() {
        alfredSharedPref.edit().clear().apply()
    }

    var currentShoppingListUuid: String
        get() = alfredSharedPref.getString(CURRENT_SHOPPING_LIST_UUID, "") ?: ""
        set(uuid) = alfredSharedPref.edit().putString(
            CURRENT_SHOPPING_LIST_UUID,
            uuid
        ).apply()
}