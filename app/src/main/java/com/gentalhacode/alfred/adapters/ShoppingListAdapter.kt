package com.gentalhacode.alfred.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewGrocery
import com.gentalhacode.util.Action
import com.gentalhacode.util.ParamBlock

/**
 * .:.:.:. Created by @thgMatajs on 26/02/20 .:.:.:.
 */
class ShoppingListAdapter(
    private val addProductOnClick: ParamBlock<ViewGrocery>,
    private val addUserOnClick: ParamBlock<ViewGrocery>
) : ListAdapter<ViewGrocery, ShoppingListAdapter.ViewHolder>(ShoppingDiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), addProductOnClick, addUserOnClick)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(grocery: ViewGrocery, addProductOnClick: ParamBlock<ViewGrocery>, addUserOnClick: ParamBlock<ViewGrocery>) {
            itemView.run {
                findViewById<TextView>(R.id.itemListName).text = grocery.name
                findViewById<TextView>(R.id.itemListTxtTotalProducts).text = grocery.products.size.toString()
                findViewById<ImageButton>(R.id.itemListBtnAddUser).setOnClickListener {
                    addUserOnClick.invoke(grocery)
                }
                findViewById<ImageButton>(R.id.itemListBtnAddProduct).setOnClickListener {
                    addProductOnClick.invoke(grocery)
                }
            }
        }
    }
}

class ShoppingDiffUtilCallBack : DiffUtil.ItemCallback<ViewGrocery>() {
    override fun areItemsTheSame(oldItem: ViewGrocery, newItem: ViewGrocery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ViewGrocery, newItem: ViewGrocery): Boolean {
        return oldItem == newItem
    }

}