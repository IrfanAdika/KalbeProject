package com.kalbe.core.extensions

import android.util.Log
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import coil.request.ImageRequest
import com.kalbe.core.ui.BottomSheetAlert
import java.util.*

/**
 * Load image using picasso library
 */
fun ImageView.loadUrl(url: String?, scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP) {
    this.scaleType = scaleType
    url?.let {
        if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
            val imageLoader = ImageLoader.Builder(this.context)
                .componentRegistry {
                    add(SvgDecoder(this@loadUrl.context))
                }
                .build()
            val request = ImageRequest.Builder(this.context)
                .data(it)
                .target(this)
//                .placeholder(R.drawable.icon_place_holder)
//                .error(R.drawable.icon_place_holder)
                .build()
            imageLoader.enqueue(request)
        } else {
            this.load(url) {
//                placeholder(R.drawable.icon_place_holder)
//                error(R.drawable.icon_place_holder)
            }
        }
    }
}

fun ImageView.loadDrawable(image: Int) {
    this.load(image)
}

/**
 * Change string to capitalize word
 * ex : Irfan Adika
 */
fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.toLowerCase().capitalize() }

/**
 * Show Bottom dialog
 * for pop up alert.
 * ex: Pop up No connection, Time Out Request, success OVO Payment
 */
fun FragmentManager.bottomSheetDialog(
    textButton: String,
    message: String,
    withButton: Boolean,
    callback: BottomSheetAlert.PopUpAlertCallback? = null,
    title: String = "",
    image: Int = 0,
    allowCancelable: Boolean = true,
    dismissWhenResume: Boolean = true
) {
    try {
        val fragment = BottomSheetAlert()
        fragment.textButton = textButton
        fragment.message = message
        fragment.title = title
        fragment.image = image
        fragment.dismissWhenResume = dismissWhenResume
        fragment.allowCancel = allowCancelable
        if (callback != null) {
            fragment.callback = callback
        }
        fragment.withButton = withButton
        fragment.show(this, "popup_alert")
    } catch (e: IllegalStateException) {
        Log.i("CannotShowDialog", "true")
    }
}

fun DialogFragment.showFragment(
    fm: FragmentManager,
    tag: String
) {
    if (fm.findFragmentByTag(tag) == null) {
        show(fm, tag)
    }
}