package com.cacagdas.productsapp.presentation.products

import com.cacagdas.productsapp.core.base.ProductsAppViewModel
import com.cacagdas.productsapp.core.util.extension.checkResult
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.domain.GetProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProducts: GetProducts
) : ProductsAppViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    init {
        getProductList()
    }

    private fun getProductList() = viewModelLaunch {
        showLoading.value = true
        checkResult(getProducts.invoke(GetProducts.Params(Unit))) {
            it.products?.let { list -> _products.value = list }
        }
    }
}
