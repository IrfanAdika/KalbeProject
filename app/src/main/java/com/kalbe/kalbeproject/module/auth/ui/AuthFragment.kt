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
import com.kalbe.kalbeproject.databinding.FragmentAuthBinding
import com.kalbe.kalbeproject.module.auth.di.AuthModule
import com.kalbe.kalbeproject.module.auth.viewmodel.AuthViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AuthFragment: BaseFragment() {

    private var viewBinding: FragmentAuthBinding? = null
    private val viewModel: AuthViewModel by viewModel()
    private var email = ""
    private var password = ""
    private var authType: Authtype = Authtype.REGISTER

    init {
        AuthModule.load()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)
        viewBinding?.viewModel = viewModel
        viewBinding?.lifecycleOwner = this
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModel()
        listenerEditTextFilled()
        buttonClicked()
        switchAuthType()
    }

    private fun switchAuthType() {
        when (authType) {
            Authtype.LOGIN -> {
                viewBinding?.textviewTitle?.text = getString(R.string.login)
                viewBinding?.buttonRegister?.text = getString(R.string.login)
                viewBinding?.textviewSwitchAction?.text = getString(R.string.dont_have_account)
            }

            Authtype.REGISTER -> {
                viewBinding?.textviewTitle?.text = getString(R.string.register)
                viewBinding?.buttonRegister?.text = getString(R.string.register)
                viewBinding?.textviewSwitchAction?.text = getString(R.string.already_account)
            }
        }
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
            requestAuthToServer()
        }

        viewBinding?.textviewSwitchAction?.setOnClickListener {
            authType = if (authType == Authtype.LOGIN) Authtype.REGISTER else Authtype.LOGIN
            switchAuthType()
        }
    }

    private fun requestAuthToServer() {
        when (authType) {
            Authtype.LOGIN -> {
                viewModel.login(email = email, password = password)
            }

            Authtype.REGISTER -> {
                viewModel.register(email = email, password = password)
            }
        }
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
                    showSnackbar("Success Register, please login")
                    switchAuthType()
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })

        viewModel.loginFormResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer

            when (result) {
                is Result.Success -> {
                    showSnackbar("Login Success")
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })
    }
}