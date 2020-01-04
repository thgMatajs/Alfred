package com.gentalhacode.alfred.bottomsheet

import android.view.View
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.util.ParamBlock
import com.gentalhacode.util.ViewAction
import com.gentalhacode.util.value
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.textfield.TextInputEditText


/**
 * .:.:.:. Created by @thgMatajs on 04/01/20 .:.:.:.
 */
class AddUserBottomSheet : BaseBottomSheetDialogFragment() {

    private var clickOnAdd: ParamBlock<String> = {}
    private var clickOnCancel: ViewAction = {}
    private lateinit var edtEmail: TextInputEditText
    private lateinit var btnCancel: ExtendedFloatingActionButton
    private lateinit var btnAdd: ExtendedFloatingActionButton

    override val layoutResourceId: Int = R.layout.add_user_layout

    override fun buildView(view: View) {
        view.apply {
            btnCancel = findViewById(R.id.addUserBtnCancel)
            btnAdd = findViewById(R.id.addUserBtnAdd)
            edtEmail = findViewById(R.id.addUserEdtEmail)
        }
    }

    override fun initActionsViews() {
        btnCancel.setOnClickListener { clickOnCancel(it) }
        btnAdd.setOnClickListener { clickOnAdd(edtEmail.value()) }
    }

    fun setOnClickAdd(action: ParamBlock<String>) {
        clickOnAdd = action
    }

    fun setOnClickCancel(action: ViewAction) {
        clickOnCancel = action
    }

    companion object {
        const val TAG = "AddUserBottomSheet"
    }
}