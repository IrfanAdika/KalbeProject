package com.kalbe.kalbeproject.module.product.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.kalbe.datasource.model.Product
import com.kalbe.datasource.model.Result
import com.kalbe.datasource.remote.ApiRepository
import com.kalbe.datasource.remote.Resource
import kotlinx.coroutines.launch

class ProductViewModel(private val apiRepository: ApiRepository): ViewModel() {

    private var _addProductFormResult = MutableLiveData<Result<Product>>()
    val addProductFormResult: LiveData<Result<Product>> get() = _addProductFormResult

    fun addProduct(product: Product) = viewModelScope.launch {

        val body = JsonObject()
        body.addProperty("sku", product.sku)
        body.addProperty("product_name", product.productName)
        body.addProperty("qty", product.qty)
        body.addProperty("price", product.price)
        body.addProperty("unit", product.unit)
        body.addProperty("status", product.status)

        val response = apiRepository.addProduct(body = body)

        when (response) {
            is Resource.Success -> {
                _addProductFormResult.value = Result.Success(value = response.value)
            }

            is Resource.Failure -> {
                _addProductFormResult.value = Result.Failure()
            }
        }

    }

    private var _getProductsFormResult = MutableLiveData<Result<List<Product>>>()
    val getProductsFormResult: LiveData<Result<List<Product>>> get() = _getProductsFormResult

    fun getProducts() = viewModelScope.launch {
        val response = apiRepository.getProducts()

        when (response) {
            is Resource.Success -> {
                val products = response.value
                _getProductsFormResult.value = Result.Success(value = products)
            }

            is Resource.Failure -> {
                _getProductsFormResult.value = Result.Failure(message = response.errorMessage)
            }
        }
    }

    private var _isEnableButton = MutableLiveData<Boolean>()
    val isEnableButton: LiveData<Boolean> get() = _isEnableButton

    fun checkField(product: Product) {
        _isEnableButton.value = !(product.sku.isEmpty() || product.productName.isEmpty() || product.qty == 0 || product.price == 0 || product.unit.isEmpty())
    }
}