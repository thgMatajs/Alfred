package com.gentalhacode.alfred.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.gentalhacode.alfred.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * .:.:.:. Created by @thgMatajs on 04/01/20 .:.:.:.
 */
abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment() {

    abstract fun buildView(view: View)

    abstract fun initActionsViews()

    abstract val layoutResourceId: Int

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResourceId, container, false)
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
}