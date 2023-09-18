package com.cacagdas.productsapp.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.cacagdas.productsApp.R
import com.cacagdas.productsApp.databinding.ProductDetailFragmentBinding
import com.cacagdas.productsapp.core.base.ProductsAppFragment
import com.cacagdas.productsapp.core.util.extension.loadImage
import com.cacagdas.productsapp.core.util.extension.observeLiveData
import com.cacagdas.productsapp.core.widget.WidgetProgressDialog
import com.cacagdas.productsapp.core.widget.WidgetToolbar
import com.cacagdas.productsapp.data.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : ProductsAppFragment<ProductDetailFragmentBinding, DetailViewModel>() {

    override val viewModel: DetailViewModel by viewModels()

    private val progressDialog by lazy {
        WidgetProgressDialog(requireContext()).also {
            lifecycle.addObserver(it)
        }
    }

    companion object {
        const val ARG_PRODUCT_ID = "productId"
        const val ARG_PRODUCT_TITLE = "productTitle"
    }

    override fun onBindView(binding: ProductDetailFragmentBinding) {
        // no-op
    }

    override fun observeViewModel() {
        viewModel.run {
            observeLiveData(showLoadingLiveData) {
                progressDialog.showOrHide(it)
            }
            observeLiveData(product) {
                bindProduct(it)
            }
        }
    }

    private fun bindProduct(product: Product) {
        binding.run {
            image.loadImage(product.image)
            price.text = product.price.toString()
            detail.text = product.description
        }
    }

    override fun provideToolbar() = WidgetToolbar(
        title = viewModel.productTitle ?: getString(R.string.detail),
    )

    override fun provideBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> ProductDetailFragmentBinding {
        return ProductDetailFragmentBinding::inflate
    }
}