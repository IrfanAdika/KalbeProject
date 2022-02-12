package com.kalbe.core.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.*

class BroadcastReceivers(private val context: Context) {
    fun addObserver(
        notification: NotificationType,
        responseHandler: BroadcastReceiver?
    ) {
        LocalBroadcastManager.getInstance(context)
            .registerReceiver(responseHandler!!, IntentFilter(notification.name))
    }

    fun removeObserver(
        responseHandler: BroadcastReceiver?
    ) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(responseHandler!!)
    }

    fun postNotification(
        notification: NotificationType,
        params: HashMap<String, String>? = HashMap()
    ) {
        val intent = Intent(notification.name)
        // insert parameters if needed
        if (params != null) {
            for ((key, value) in params) {
                intent.putExtra(key, value)
            }
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    enum class NotificationType {
        UNAUTHORIZED
    }
}