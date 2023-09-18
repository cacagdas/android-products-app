package com.cacagdas.productsapp.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.cacagdas.productsapp.core.base.ProductsAppViewModel
import com.cacagdas.productsapp.core.util.extension.checkResult
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.domain.GetProductDetail
import com.cacagdas.productsapp.presentation.detail.DetailFragment.Companion.ARG_PRODUCT_ID
import com.cacagdas.productsapp.presentation.detail.DetailFragment.Companion.ARG_PRODUCT_TITLE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getProductDetail: GetProductDetail
) : ProductsAppViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> = _product

    private val productId = savedStateHandle.get<String>(ARG_PRODUCT_ID)
    val productTitle = savedStateHandle.get<String?>(ARG_PRODUCT_TITLE)

    init {
        getProduct()
    }

    private fun getProduct() = viewModelLaunch {
        productId?.let {
            showLoading.value = true
            checkResult(getProductDetail.invoke(GetProductDetail.Params(productId))) {
                _product.value = it
                showLoading.value = false
            }
        }
    }
}
