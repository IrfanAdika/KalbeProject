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
import androidx.navigation.fragment.navArgs
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
    val args : AddProductFragmentArgs by navArgs()

    private var sku = ""
    private var productName = ""
    private var qty = 0
    private var price = 0
    private var unit = ""
    private var status = 0
    val statusList = arrayListOf(0, 1)

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

        sku = args.sku
        setupLayout()
    }

    private fun setupLayout() {
        val isEdit = sku.isEmpty().not()
        if (isEdit) {
            viewBinding?.textviewTitle?.text = getString(R.string.edit_product)
            viewBinding?.buttonSubmitProduct?.text = getString(R.string.edit)
            getProductBySku()
        } else {
            viewBinding?.textviewTitle?.text = getString(R.string.add_product)
            viewBinding?.buttonSubmitProduct?.text = getString(R.string.add)
        }
    }

    private fun buttonClicked() {
        viewBinding?.buttonSubmitProduct?.setOnClickListener {
            addProduct()
        }

        viewBinding?.buttonCancel?.setOnClickListener {
            findNavController().popBackStack()
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

        val adapterStatus = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, statusList)
        viewBinding?.spinnerStatus?.adapter = adapterStatus

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

    private fun getProductBySku() {
        showLoading()
        viewModel.getProductBySku(sku = sku)
    }

    private fun setDataBySku(product: Product) {
        viewBinding?.edittextSku?.setText(product.sku)
        viewBinding?.edittextProductName?.setText(product.productName)
        viewBinding?.edittextQty?.setText(product.qty.toString())
        viewBinding?.edittextPrice?.setText(product.price.toString())
        viewBinding?.edittextUnit?.setText(product.unit)
        viewBinding?.spinnerStatus?.setSelection(statusList.indexOf(product.status))
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

        viewModel.getProductBySkuFormResult.observe(viewLifecycleOwner, Observer {
            val result = it ?: return@Observer
            hideLoading()

            when (result) {
                is Result.Success -> {
                    setDataBySku(product = result.value)
                }

                is Result.Failure -> {
                    showSnackbar(result.message)
                }
            }
        })
    }
}