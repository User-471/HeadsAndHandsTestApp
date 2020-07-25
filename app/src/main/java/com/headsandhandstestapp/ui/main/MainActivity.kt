package com.headsandhandstestapp.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.headsandhandstestapp.R
import com.headsandhandstestapp.ui.base.BaseActivity
import com.headsandhandstestapp.utils.*
import com.headsandhandstestapp.utils.connection.observeConnectionActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainViewModel

    private val cities = arrayListOf("Kazan", "Moscow", "London", "Beijing", "New York", "Tokyo", "Canberra", "El Quique")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java).init(kodein)

        observeErrorMessage()
        observeLoadingData()
        setOnClickListener()
        setTextChangedListeners()
        setEditTextActionButtonsListener()
        observeConnectionActivity(
            this,
            this,
            cl_activity_main,
            { onConnected() },
            { onDisconnected() })

        showKeyboard(this, et_email)
    }

    override fun onDestroy() {
        super.onDestroy()

        hideKeyboard(
            this,
            if (et_email.isFocused) et_email else et_password,
            cl_activity_main
        )
    }

    private fun setOnClickListener() {
        ib_back.setOnClickListener {
            finish()
        }
        b_sign_in.setOnClickListener {
            onLoginClicked()
        }
    }

    private fun onLoginClicked() {

        val arr = arrayListOf<Pair<Boolean, String>>()

        arr.add(Pair((et_email.text?.toString() ?: "").isNotBlank(), getString(R.string.input_all_fields)))
        arr.add(Pair((et_password.text?.toString() ?: "").isNotBlank(), getString(R.string.input_all_fields)))
        arr.add(Pair((et_email.text?.toString() ?: "").isEmail(), getString(R.string.input_correct_email)))
        arr.add(Pair((et_password.text?.toString() ?: "").isAcceptablePassword(), getString(R.string.password_not_allowed)))

        for (i in arr) {
            if (!i.first) {
                Snackbar.make(cl_activity_main, i.second, Snackbar.LENGTH_LONG)
                    .withColor(ContextCompat.getColor(this, R.color.red))
                    .apply { this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 10 }
                    .show()

                return
            }
        }

        checkWeather()
    }

    private fun checkWeather() {
        viewModel.getCityWeather("aef48283d300ec2559b076464f6a67f9", cities.random()).observe(this, Observer {
            it?.let {
                Snackbar.make(cl_activity_main, "${it.location.name}, ${it.current.temperature} °C", Snackbar.LENGTH_LONG)
                    .withColor(ContextCompat.getColor(this, R.color.grey))
                    .apply { this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 10 }
                    .show()
            }
        })
    }

    private fun onDisconnected() {
        cl_no_connection.visibility = View.VISIBLE
        cl_content.visibility = View.GONE
        tv_toolbar_create.visibility = View.GONE

        hideKeyboard(
            this,
            if (et_email.isFocused) et_email else et_password,
            cl_activity_main
        )
    }

    private fun onConnected() {
        cl_no_connection.visibility = View.GONE
        cl_content.visibility = View.VISIBLE
        checkEditTexts()

        showKeyboard(this, et_email)
    }

    private fun checkEditTexts() {
        if ((et_email.text?.toString() ?: "").isEmail()
            && (et_email.text?.toString() ?: "").isNotBlank()
            && (et_password.text?.toString() ?: "").isAcceptablePassword()
            && (et_password.text?.toString() ?: "").isNotBlank()
        ) {
            tv_toolbar_create.visibility = View.VISIBLE
        } else {
            tv_toolbar_create.visibility = View.GONE
        }
    }

    private fun setEditTextActionButtonsListener() {
        et_password.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE ||
                event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                onLoginClicked()
                true
            } else {
                false
            }
        }
    }

    private fun setTextChangedListeners() {
        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                checkEditTexts()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

                checkEditTexts()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeLoadingData() {
        viewModel.loadingLiveData.observe(this, Observer {
            if (it != null && it) {
                ll_loading.visibility = View.VISIBLE
            } else {
                ll_loading.visibility = View.GONE
            }
        })
    }

    private fun observeErrorMessage() {
        viewModel.errorMessageData.observe(this, Observer {
            Snackbar.make(
                cl_activity_main,
                it.toString(),
                Snackbar.LENGTH_SHORT)
                .withColor(resources.getColor(R.color.red))
                .apply { this.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).maxLines = 10 }
                .show()

            ll_loading.visibility = View.GONE
        })
    }
}