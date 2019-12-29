package com.gentalhacode.alfred.adapters

import androidx.recyclerview.widget.DiffUtil
import com.gentalhacode.alfred.model.ViewProduct

/**
 * .:.:.:. Created by @thgMatajs on 29/12/19 .:.:.:.
 */
class DiffUtilCallBack : DiffUtil.ItemCallback<ViewProduct>() {

    override fun areItemsTheSame(oldItem: ViewProduct, newItem: ViewProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ViewProduct, newItem: ViewProduct): Boolean {
        return oldItem == newItem
    }
}