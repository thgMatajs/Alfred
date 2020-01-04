package com.gentalhacode.alfred.bottomsheet

import android.view.View
import android.widget.ImageView
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.util.ParamBlock
import com.gentalhacode.util.ViewAction
import com.gentalhacode.util.randomUuid
import com.gentalhacode.util.value
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
class ProductBottomSheet : BaseBottomSheetDialogFragment() {

    private var clickOnAdd: ParamBlock<ViewProduct> = {}
    private var clickOnCancel: ViewAction = {}
    private lateinit var ivProduct: ImageView
    private lateinit var btnAddPhoto: FloatingActionButton
    private lateinit var edtName: TextInputEditText
    private lateinit var edtAmount: TextInputEditText
    private lateinit var edtBrand: TextInputEditText
    private lateinit var btnCancel: ExtendedFloatingActionButton
    private lateinit var btnAdd: ExtendedFloatingActionButton

    override val layoutResourceId: Int = R.layout.product_layout

    override fun buildView(view: View) {
        view.apply {
            ivProduct = findViewById(R.id.productIv)
            btnAddPhoto = findViewById(R.id.productBtnAddPhoto)
            edtName = findViewById(R.id.productEdtName)
            edtAmount = findViewById(R.id.productEdtAmount)
            edtBrand = findViewById(R.id.productEdtBrand)
            btnCancel = findViewById(R.id.productBtnCancel)
            btnAdd = findViewById(R.id.productBtnAdd)
        }
    }

    override fun initActionsViews() {
        btnCancel.setOnClickListener { clickOnCancel(it) }
        btnAdd.setOnClickListener { clickOnAdd(makeProduct()) }
    }

    fun setOnClickAdd(action: ParamBlock<ViewProduct>) {
        clickOnAdd = action
    }

    fun setOnClickCancel(action: ViewAction) {
        clickOnCancel = action
    }

    private fun makeProduct() = ViewProduct(
        id = randomUuid(),
        image = "",
        name = edtName.value(),
        amount = edtAmount.value(),
        brand = edtBrand.value(),
        price = "",
        isInTheCart = false,
        barcode = ""
    )

    companion object {
        const val TAG = "ProductBottomSheet"
    }
}