package com.kalbe.kalbeproject.module.product.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.kalbe.core.ui.BaseFragment
import com.kalbe.datasource.model.Product
import com.kalbe.datasource.model.Result
import com.kalbe.kalbeproject.R
import com.kalbe.kalbeproject.databinding.FragmentProductBinding
import com.kalbe.kalbeproject.module.product.di.ProductModule
import com.kalbe.kalbeproject.module.product.ui.adapter.ProductAdapter
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
        getProducts()
    }

    private fun buttonClicked() {
        viewBinding?.buttonAddProduct?.setOnClickListener {
            val action = ProductFragmentDirections.actionProductFragmentToAddProductFragment()
            it.findNavController().navigate(action)
        }
    }

    private fun getProducts() {
        showLoading()
        viewModel.getProducts()
    }

    private fun deleteProduct(sku: String) {
        showLoading()
        viewModel.removeProduct(sku = sku)
    }

    private fun observerViewModel() {
        viewModel.getProductsFormResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer
            hideLoading()

            when (result) {
                is Result.Success -> {
                    val products = result.value as ArrayList<Product>
                    val adapter = ProductAdapter(products = products, callback = object : ProductAdapter.ProductItemCallback {
                        override fun onEditClicked(sku: String) {
                            val action = ProductFragmentDirections.actionProductFragmentToAddProductFragment(sku)
                            findNavController().navigate(action)
                        }

                        override fun onDeleteClicked(sku: String) {
                            deleteProduct(sku)
                        }

                    })

                    viewBinding?.recyclerviewProduct?.adapter = adapter
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })

        viewModel.removeFormResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer
            hideLoading()

            when (result) {
                is Result.Success -> {
                    getProducts()
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })
    }

}