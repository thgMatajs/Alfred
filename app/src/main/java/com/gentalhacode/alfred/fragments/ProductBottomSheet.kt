package com.gentalhacode.alfred.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.util.ParamBlock
import com.gentalhacode.util.ViewAction
import com.gentalhacode.util.randomUuid
import com.gentalhacode.util.value
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
class ProductBottomSheet : BottomSheetDialogFragment() {

    private var clickOnAdd: ParamBlock<ViewProduct> = {}
    private var clickOnCancel: ViewAction = {}
    private lateinit var ivProduct: ImageView
    private lateinit var btnAddPhoto: FloatingActionButton
    private lateinit var edtName: TextInputEditText
    private lateinit var edtAmount: TextInputEditText
    private lateinit var edtBrand: TextInputEditText
    private lateinit var btnCancel: ExtendedFloatingActionButton
    private lateinit var btnAdd: ExtendedFloatingActionButton

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.product_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildView(view)
        initActionsViews()
        dialog?.setOnShowListener { dialog ->
            val bottomSheet =
                (dialog as BottomSheetDialog).findViewById<FrameLayout>(R.id.design_bottom_sheet)
            BottomSheetBehavior.from<View>(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    private fun buildView(v: View) {
        v.apply {
            ivProduct = findViewById(R.id.productIv)
            btnAddPhoto = findViewById(R.id.productBtnAddPhoto)
            edtName = findViewById(R.id.productEdtName)
            edtAmount = findViewById(R.id.productEdtAmount)
            edtBrand = findViewById(R.id.productEdtBrand)
            btnCancel = findViewById(R.id.productBtnCancel)
            btnAdd = findViewById(R.id.productBtnAdd)
        }
    }

    private fun initActionsViews() {
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