package com.kalbe.core.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.kalbe.core.R
import com.kalbe.core.connection.NetworkConnection
import com.kalbe.core.databinding.ViewEmptyListBinding
import com.kalbe.core.utils.BroadcastReceivers
import com.kalbe.core.widget.ProgressDialog
import org.jetbrains.anko.alert
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity(), BaseView {
    private val broadcastReceiver: BroadcastReceivers by inject()
    private val internetConnectivity: NetworkConnection by inject()
    private val loadingDialogFragment by lazy { ProgressDialog() }
    private var startTime: Long = 0
    private lateinit var emptyView: ViewEmptyListBinding
    private var snackbar: Snackbar? = null
    var isUnAuthorized = MutableLiveData<Boolean>()

    /**
     * Broadcastreceiver from Retrofit config
     * receive when unauthorized
     */
    private val broadReceiverUnAuth = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            observerUnAuthorized()
        }
    }

    override fun onStart() {
        super.onStart()

        initSnackBar()
        observerBroadcastReceiver()
        observerInternetConnection()
    }

    override fun onResume() {
        super.onResume()

        startTime = System.currentTimeMillis()
    }

    override fun onPause() {
        super.onPause()
        removeObserver()
    }

    private fun initSnackBar() {
        snackbar = Snackbar.make(window.decorView.rootView, "", Snackbar.LENGTH_INDEFINITE)
        snackbar?.setAction("Ok") { // Call your action method here
            snackbar?.dismiss()
        }

    }

    private fun observerBroadcastReceiver() {
        broadcastReceiver.addObserver(
            BroadcastReceivers.NotificationType.UNAUTHORIZED,
            broadReceiverUnAuth
        )
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

    /**
     * When unauthorized
     * go to welcome page
     */
    private fun observerUnAuthorized() {
        isUnAuthorized.value = true
        broadcastReceiver.removeObserver(broadReceiverUnAuth)
    }

    /**
     * Show loading
     */
    override fun showLoading() {
        //Show Loader
        if (!loadingDialogFragment.isAdded){
            loadingDialogFragment.show(supportFragmentManager, "loader")
        }

    }

    /**
     * Hide loading
     */
    override fun hideLoading() {
        if (loadingDialogFragment.isAdded) {
            loadingDialogFragment.dismissAllowingStateLoss()
        }
    }

    /**
     * Show snackbar
     */
    override fun showSnackbar(errorMessage: String?, motionType: String) {
        if (!errorMessage.isNullOrEmpty()) {
            val colorView: Int = when (motionType) {
                ToastType.ERROR.toString() -> {
                    R.color.colorRed
                }
                ToastType.WARNING.toString() -> {
                    R.color.colorLightYellow
                }
                else -> {
                    R.color.colorPrimary
                }
            }

            snackbar?.setText(errorMessage)
            snackbar?.view?.setBackgroundColor(ContextCompat.getColor(this, colorView))
            snackbar?.setActionTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            snackbar?.show()
        }
    }

    /**
     * Show alert
     */
    override fun showAlert(
        title: String,
        message: String,
        textBtnPositive: String,
        textBtnNegative: String,
        positiveAction: () -> Unit,
        negativeAction: () -> Unit,
        cancelable: Boolean
    ) {
        alert(message, title) {
            positiveButton(textBtnPositive) {
                positiveAction()
            }

            if (textBtnNegative.isNotEmpty()) {
                negativeButton(textBtnNegative) {
                    negativeAction()
                }
            }
        }.show().setCancelable(cancelable)
    }

    /**
     * Add empty list
     * used for page contain list data
     */
    override fun addEmptyList(
        view: ViewGroup,
        image: Int,
        title: String,
        subTitle: String
    ) {

        if (view.findViewById<ViewGroup>(R.id.view_empty) == null) {
            val layoutInflater: LayoutInflater = LayoutInflater.from(this)
            emptyView = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.view_empty_list, // Custom view/ layout
                view, // Root layout to attach the view
                false // Attach with root layout or not
            )

            emptyView.imageEmpty.setImageResource(image)
            emptyView.titleEmpty.text = title
            emptyView.subtitleEmpty.text = subTitle
            view.addView(emptyView.root)

        }
    }

    /**
     * Hide empty list
     */
    override fun removeEmptyList(view: ViewGroup) {
        if (this::emptyView.isInitialized) {
            Handler(Looper.getMainLooper()).post { view.removeView(emptyView.root) }
        }
    }

    /**
     * Remove observer
     * when activity onPause
     */
    private fun removeObserver() {
        broadcastReceiver.removeObserver(broadReceiverUnAuth)
    }

    /**
     * Get duration on page
     * usually for analytics
     */
    override fun durationPage(): Long {
        val endTime = System.currentTimeMillis()
        val duration = endTime - startTime
        Log.i("durationpageactivity", duration.toString())
        return duration
    }

    /**
     * Hide keyboard
     * when tap any view
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * Callback connection
     */
    override fun onNoConnection(isConnected: Boolean) {
        if (isConnected) {
            snackbar?.dismiss()
        } else {
            snackbar?.setText("No Internet")
            snackbar?.view?.setBackgroundColor(ContextCompat.getColor(this, R.color.colorRed))
            snackbar?.setActionTextColor(ContextCompat.getColor(this, R.color.colorBlack))
            snackbar?.show()
        }
    }
}