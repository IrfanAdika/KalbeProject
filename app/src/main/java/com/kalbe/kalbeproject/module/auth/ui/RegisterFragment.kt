package com.kalbe.kalbeproject.module.auth.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.kalbe.core.ui.BaseFragment
import com.kalbe.datasource.model.Result
import com.kalbe.kalbeproject.R
import com.kalbe.kalbeproject.databinding.FragmentRegisterBinding
import com.kalbe.kalbeproject.module.auth.di.RegisterModule
import com.kalbe.kalbeproject.module.auth.viewmodel.AuthViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RegisterFragment: BaseFragment() {

    private var viewBinding: FragmentRegisterBinding? = null
    private val viewModel: AuthViewModel by viewModel()
    private var email = ""
    private var password = ""

    init {
        RegisterModule.load()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        viewBinding?.viewModel = viewModel
        viewBinding?.lifecycleOwner = this
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModel()
        listenerEditTextFilled()
        buttonClicked()
    }

    private fun listenerEditTextFilled() {
        viewBinding?.edittextEmail?.doAfterTextChanged {
            email = it.toString()
            checkField()
        }

        viewBinding?.edittextPassword?.doAfterTextChanged {
            password = it.toString()
            checkField()
        }
    }

    private fun checkField() {
        viewModel.checkField(email = this.email, password = this.password)
    }

    private fun buttonClicked() {
        viewBinding?.buttonRegister?.setOnClickListener {
            register()
        }
    }

    private fun register() {
        viewModel.register(email = email, password = password)
    }

    private fun resetField() {
        viewBinding?.apply {
            edittextEmail.setText("")
            edittextPassword.setText("")
        }
    }

    private fun observerViewModel() {
        viewModel.registerFormResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer

            when (result) {
                is Result.Success -> {
                    resetField()
                    showSnackbar("Success Register")
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })
    }
}