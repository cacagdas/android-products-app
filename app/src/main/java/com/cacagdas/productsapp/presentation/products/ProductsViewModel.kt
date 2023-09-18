package com.cacagdas.productsapp.presentation.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cacagdas.productsapp.core.base.ProductsAppViewModel
import com.cacagdas.productsapp.core.util.extension.checkResult
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.domain.GetLocalProducts
import com.cacagdas.productsapp.domain.GetProducts
import com.cacagdas.productsapp.domain.StoreProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProducts: GetProducts,
    private val storeProducts: StoreProducts,
    private val getLocalProducts: GetLocalProducts
) : ProductsAppViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    private val emptyViewVisibility = MutableLiveData<Boolean>()
    val emptyViewVisibilityLiveData: LiveData<Boolean> = emptyViewVisibility

    init {
        getProductList()
    }

    private fun getProductList() = viewModelLaunch {
        showLoading.value = true
        checkResult(getProducts.invoke(GetProducts.Params(Unit)),
            onSuccess = {
                it.products?.let { list ->
                    storeProductList(list)
                }
            }
        ) {
            getLocalProductList()
        }
    }

    private fun storeProductList(items: List<Product>)  = viewModelLaunch {
        checkResult(storeProducts.invoke(StoreProducts.Params(items)),
            onSuccess = {
                getLocalProductList()
            }
        ) {
            // no- op
        }
    }

    private fun getLocalProductList() = viewModelLaunch {
        checkResult(getLocalProducts.invoke(GetLocalProducts.Params(Unit)),
            onSuccess = {
                emptyViewVisibility.value = it.isEmpty()
                _products.value = it
            }
        ) {
            showLoading.value = false
            showErrorMessage.value = it
        }
    }

}
