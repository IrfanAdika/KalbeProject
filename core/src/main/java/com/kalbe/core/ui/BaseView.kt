package com.kalbe.core.ui

import android.view.ViewGroup

interface BaseView {
    fun showLoading()
    fun hideLoading()
    fun showSnackbar(errorMessage: String?, motionType: String = ToastType.SUCCESS.toString())
    fun showAlert(title: String, message: String, textBtnPositive: String, textBtnNegative: String, positiveAction: () -> Unit, negativeAction: () -> Unit, cancelable: Boolean = true)
    fun addEmptyList(view: ViewGroup, image: Int, title: String, subTitle: String)
    fun removeEmptyList(view: ViewGroup)
    fun durationPage(): Long
    fun onNoConnection(isConnected: Boolean)
}