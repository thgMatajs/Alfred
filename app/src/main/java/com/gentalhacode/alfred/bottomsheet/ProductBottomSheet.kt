package com.gentalhacode.alfred.bottomsheet

import android.content.pm.PackageManager
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.gentalhacode.alfred.R
import com.gentalhacode.alfred.model.ViewProduct
import com.gentalhacode.alfred.presentation.extensions.loggerE
import com.gentalhacode.alfred.presentation.extensions.loggerS
import com.gentalhacode.util.*
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import io.fotoapparat.Fotoapparat
import io.fotoapparat.result.WhenDoneListener
import io.fotoapparat.view.CameraView

/**
 * .:.:.:. Created by @thgMatajs on 30/12/19 .:.:.:.
 */
class ProductBottomSheet : BaseBottomSheetDialogFragment() {

    private var clickOnAdd: ParamBlock<ViewProduct> = {}
    private var clickOnCancel: ViewAction = {}
    private lateinit var ivProduct: CameraView
    private lateinit var btnAddPhoto: FloatingActionButton
    private lateinit var edtName: TextInputEditText
    private lateinit var edtAmount: TextInputEditText
    private lateinit var edtBrand: TextInputEditText
    private lateinit var btnCancel: ExtendedFloatingActionButton
    private lateinit var btnAdd: ExtendedFloatingActionButton
    private lateinit var fotoapparat: Fotoapparat

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
            checkPermission()
        }

    }

    private fun checkPermission() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(ivProduct.context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.CAMERA),
                PERMISSION_SHARED
            )
            fotoapparat = Fotoapparat(
                context = ivProduct.context,
                view = ivProduct
            )

            fotoapparat.start()
        } else {
            loggerE("DEU MERDA!!!")
        }
    }

    override fun initActionsViews() {
        btnCancel.setOnClickListener { clickOnCancel(it) }
        btnAdd.setOnClickListener {
            edtName.isBlank()
            edtBrand.isBlank()
            edtAmount.isBlank()
            if (
                !edtName.isBlank() &&
                !edtBrand.isBlank() &&
                !edtAmount.isBlank()
            )
                clickOnAdd(makeProduct())
            else
                btnAdd.context.toast("Preencha todos os campos indicados.")
        }
        btnAddPhoto.setOnClickListener {
            fotoapparat
                .takePicture()
                .toBitmap()
                .whenAvailable {
                    loggerS(it.toString())
                    fotoapparat.stop()
                }
        }
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
        const val PERMISSION_SHARED = 1
    }
}