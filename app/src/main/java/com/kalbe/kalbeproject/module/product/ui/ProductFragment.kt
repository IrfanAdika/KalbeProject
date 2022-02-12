package com.kalbe.kalbeproject.module.product.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.kalbe.core.ui.BaseFragment
import com.kalbe.kalbeproject.R
import com.kalbe.kalbeproject.databinding.FragmentProductBinding
import com.kalbe.kalbeproject.module.product.di.ProductModule
import com.kalbe.kalbeproject.module.product.viewmodel.ProductViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProductFragment: BaseFragment() {

    private var viewBinding: FragmentProductBinding? = null
    private val viewModel: ProductViewModel by viewModel()

    init {
        ProductModule.load()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)

        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observerViewModel()
        buttonClicked()
    }

    private fun buttonClicked() {
        viewBinding?.buttonAddProduct?.setOnClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToAddProductFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun observerViewModel() {

    }
}