package com.gentalhacode.alfred.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewProduct

/**
 * .:.:.:. Created by @thgMatajs on 29/12/19 .:.:.:.
 */
class ShoppingListAdapter(
    private val isChecked: (ViewProduct) -> Unit
) : ListAdapter<ViewProduct, ShoppingListAdapter.ViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_product_layout,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), isChecked)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: ViewProduct, checked: (ViewProduct) -> Unit) {
            itemView.run {
                findViewById<TextView>(R.id.itemProductTvTitle).text = product.name
                findViewById<TextView>(R.id.itemProductTvAmount).text = product.amount
                findViewById<TextView>(R.id.itemProductTvBrand).text = product.brand
//                findViewById<ImageView>(R.id.itemProductIv)
                findViewById<CheckBox>(R.id.itemProductCb).apply {
                    isChecked = product.isInTheCart
                    setOnCheckedChangeListener { _, _isChecked ->
                        product.isInTheCart = _isChecked
                        checked.invoke(product)
                    }
                }
            }
        }
    }
}