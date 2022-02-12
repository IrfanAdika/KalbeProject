package com.kalbe.kalbeproject.module.product.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.kalbe.core.ui.BaseFragment
import com.kalbe.datasource.model.Product
import com.kalbe.datasource.model.Result
import com.kalbe.kalbeproject.R
import com.kalbe.kalbeproject.databinding.FragmentAddProductBinding
import com.kalbe.kalbeproject.module.product.viewmodel.ProductViewModel
import org.jetbrains.anko.sdk27.coroutines.onItemSelectedListener
import org.koin.android.viewmodel.ext.android.viewModel

class AddProductFragment: BaseFragment() {

    private var viewBinding: FragmentAddProductBinding? = null
    private val viewModel: ProductViewModel by viewModel()

    private var sku = ""
    private var productName = ""
    private var qty = 0
    private var price = 0
    private var unit = ""
    private var status = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_product, container, false)
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

    private fun buttonClicked() {
        viewBinding?.buttonAddProduct?.setOnClickListener {
            addProduct()
        }
    }

    private fun listenerEditTextFilled() {
        viewBinding?.edittextSku?.doAfterTextChanged {
            sku = it.toString()
            checkField()
        }

        viewBinding?.edittextProductName?.doAfterTextChanged {
            productName = it.toString()
            checkField()
        }

        viewBinding?.edittextQty?.doAfterTextChanged {
            qty = it.toString().toInt()
            checkField()
        }

        viewBinding?.edittextPrice?.doAfterTextChanged {
            price = it.toString().toInt()
            checkField()
        }

        viewBinding?.edittextUnit?.doAfterTextChanged {
            unit = it.toString()
            checkField()
        }

        val statusList = arrayListOf(0, 1)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, statusList)
        viewBinding?.spinnerStatus?.adapter = adapter

        viewBinding?.spinnerStatus?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                status = parent?.getItemAtPosition(position).toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

    }

    private fun checkField() {
        viewModel.checkField(product = setProduct())
    }

    private fun setProduct(): Product {
        val product = Product()
        product.sku = sku
        product.productName = productName
        product.qty = qty
        product.price = price
        product.unit = unit
        product.status = status

        return product
    }

    private fun addProduct() {
        showLoading()
        viewModel.addProduct(product = setProduct())
    }

    private fun observerViewModel() {
        viewModel.addProductFormResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer
            hideLoading()

            when (result) {
                is Result.Success -> {
                    findNavController().popBackStack()
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })
    }
}