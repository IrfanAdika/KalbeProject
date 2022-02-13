package com.kalbe.core.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kalbe.core.R
import com.kalbe.core.databinding.BottomPopupAlertBinding
import com.kalbe.core.utils.AppHelper

class BottomSheetAlert : BottomSheetDialogFragment() {
    private var mBehavior: BottomSheetBehavior<*>? = null
    private lateinit var dialog: BottomSheetDialog
    var message = ""
    var textButton = ""
    var title = ""
    var image = 0
    var allowCancel = true
    var dismissWhenResume = true

    var callback: PopUpAlertCallback? = null
    var withButton = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = BottomPopupAlertBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        )

        if (title.isNotEmpty()) {
            view.labelTitlePopup.text = title
        }

        if (image != 0) {
            view.imagePopup.setImageResource(image)
        }

        if (!allowCancel) {
            view.viewDragable.visibility = View.GONE
            this.isCancelable = false
        }

        view.labelDescriptionPopup.text = message

        if (withButton) {
            view.buttonOk.text = textButton
            view.buttonOk.visibility = View.VISIBLE
            view.buttonOk.setOnClickListener {
                dialog.dismiss()
                callback?.onButtonClicked()
            }
        } else {
            view.buttonOk.visibility = View.GONE
        }

        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog


        dialog.setContentView(view.root)
        dialog.setCanceledOnTouchOutside(allowCancel)

        mBehavior =
            BottomSheetBehavior.from(view.root.parent as View)
        (mBehavior as BottomSheetBehavior<*>).state = BottomSheetBehavior.STATE_EXPANDED
        return dialog

    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        AppHelper.isPopupConnectionHasShow = false
        callback?.onDismissDialog()
    }

    override fun onPause() {
        super.onPause()

        if (this::dialog.isInitialized && dialog.isShowing && dismissWhenResume) {
            dialog.dismiss()
        }
    }

    /**
     * Set Theme of BottomSheet
     * it will set Round top Background
     */
    override fun getTheme(): Int {
        return R.style.DialogRoundTheme
    }

    interface PopUpAlertCallback {
        fun onButtonClicked()
        fun onDismissDialog()
    }
}