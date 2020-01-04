package com.gentalhacode.alfred.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.util.ParamBlock

/**
 * .:.:.:. Created by @thgMatajs on 29/12/19 .:.:.:.
 */
class ShoppingListAdapter(
    private val isCheckedOnClick: (ViewProduct, Boolean) -> Unit,
    private val deleteOnClick: ParamBlock<ViewProduct>
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
        holder.bind(getItem(position), isCheckedOnClick, deleteOnClick)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: ViewProduct, checked: (ViewProduct, Boolean) -> Unit,
                 deleteOnClick: ParamBlock<ViewProduct>) {
            itemView.run {
                findViewById<TextView>(R.id.itemProductTvTitle).text = product.name
                findViewById<TextView>(R.id.itemProductTvAmount).text = product.amount
                findViewById<TextView>(R.id.itemProductTvBrand).text = product.brand
//                findViewById<ImageView>(R.id.itemProductIv)
                findViewById<CheckBox>(R.id.itemProductCb).apply {
                    setOnCheckedChangeListener { _, _isChecked ->
                        checked.invoke(product, _isChecked)
                    }
                    isChecked = product.isInTheCart
                }
                findViewById<TextView>(R.id.itemProductBtnDelete).setOnClickListener {
                    deleteOnClick.invoke(product)
                }
            }
        }
    }
}