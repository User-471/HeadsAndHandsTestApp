package com.headsandhandstestapp.utils.connection

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.headsandhandstestapp.R
import com.headsandhandstestapp.ui.base.BaseActivity
import com.headsandhandstestapp.ui.base.BaseFragment
import com.headsandhandstestapp.utils.withColor

fun observeConnectionActivity(
    context: Context,
    activity: BaseActivity,
    view: View,
    onSuccessClickListener: () -> Unit,
    onFailureClickListener: () -> Unit
) {
    val connectionLiveData = ConnectionLiveData(context)
    connectionLiveData.observe(activity, androidx.lifecycle.Observer {
        it?.let {
            if (it.isConnected) {
                if (!getConnectionStatus(context)) {

                    onSuccessClickListener()
                    Snackbar.make(
                        view,
                        context.resources.getString(R.string.connected),
                        Snackbar.LENGTH_LONG
                    )
                        .withColor(context.resources.getColor(R.color.green)).show()

                    setConnectionStatus(context, true)
                }
            } else {
                Snackbar.make(
                    view,
                    context.resources.getString(R.string.no_connection),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .withColor(context.resources.getColor(R.color.red)).show()

                onFailureClickListener()
                setConnectionStatus(context, false)
            }
        }
    })
}

fun observeConnectionFragment(
    context: Context,
    baseFragment: BaseFragment,
    view: View,
    onSuccessClickListener: () -> Unit,
    onFailureClickListener: () -> Unit
) {
    val connectionLiveData = ConnectionLiveData(context)
    connectionLiveData.observe(baseFragment, androidx.lifecycle.Observer {
        it?.let {
            if (it.isConnected) {
                if (!getConnectionStatus(context)) {

                    onSuccessClickListener()
                    Snackbar.make(
                        view,
                        context.resources.getString(R.string.connected),
                        Snackbar.LENGTH_LONG
                    )
                        .withColor(context.resources.getColor(R.color.green)).show()

                    setConnectionStatus(context, true)
                }
            } else {
                Snackbar.make(
                    view,
                    context.resources.getString(R.string.no_connection),
                    Snackbar.LENGTH_INDEFINITE
                )
                    .withColor(context.resources.getColor(R.color.red)).show()
                onFailureClickListener()
                setConnectionStatus(context, false)
            }
        }
    })
}

fun setConnectionStatus(context: Context, isConnected: Boolean) {

    val sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE) ?: return
    with(sharedPref.edit()) {
        putBoolean("connection", isConnected)
        commit()
    }

}

fun getConnectionStatus(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences("app", Context.MODE_PRIVATE)

    val connectionStatus = sharedPref.getBoolean("connection", true)

    return connectionStatus
}