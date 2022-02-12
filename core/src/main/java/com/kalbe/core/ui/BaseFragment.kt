package com.kalbe.core.ui

import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.kalbe.core.connection.NetworkConnection
import org.koin.android.ext.android.inject

/**
 * All method relate from base activity
 */
abstract class BaseFragment: Fragment(), BaseView {

    private val internetConnectivity: NetworkConnection by inject()
    private lateinit var baseActivity: BaseActivity
    private var startTime: Long = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        baseActivity = activity as BaseActivity
        observerInternetConnection()
    }

    override fun showLoading() {
        baseActivity.showLoading()
    }

    override fun hideLoading() {
        baseActivity.hideLoading()
    }

    override fun showSnackbar(errorMessage: String?, motionType: String) {
        baseActivity.showSnackbar(errorMessage = errorMessage, motionType = motionType)
    }

    override fun showAlert(
        title: String,
        message: String,
        textBtnPositive: String,
        textBtnNegative: String,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit,
        cancelable: Boolean
    ) {
        baseActivity.showAlert(
            title = title,
            message = message,
            textBtnPositive = textBtnPositive,
            textBtnNegative = textBtnNegative,
            positiveAction = positiveAction,
            negativeAction = negativeAction,
            cancelable = cancelable)
    }

    override fun addEmptyList(
        view: ViewGroup,
        image: Int,
        title: String,
        subTitle: String) {
        baseActivity.addEmptyList(view, image, title, subTitle)
    }

    override fun removeEmptyList(view: ViewGroup) {
        baseActivity.removeEmptyList(view)
    }

    override fun onResume() {
        super.onResume()

        startTime = System.currentTimeMillis()
    }

    override fun durationPage(): Long {
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        Log.i("durationpagefragment", duration.toString())
        return duration
    }

    private fun observerInternetConnection() {
        internetConnectivity.observe(this, Observer {
            val isConnected = it ?: return@Observer

            if (isConnected) {
                onNoConnection(true)
            } else {
                onNoConnection(false)
            }
        })
    }

    override fun onNoConnection(isConnected: Boolean) {
        baseActivity.onNoConnection(isConnected)
    }
}