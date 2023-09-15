package com.cacagdas.productsapp.presentation.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.cacagdas.productsApp.R
import com.cacagdas.productsApp.databinding.ProductsFragmentBinding
import com.cacagdas.productsapp.core.base.ProductsAppFragment
import com.cacagdas.productsapp.core.util.extension.observeFlow
import com.cacagdas.productsapp.core.util.extension.observeLiveData
import com.cacagdas.productsapp.core.widget.WidgetProgressDialog
import com.cacagdas.productsapp.core.widget.WidgetToolbar
import com.cacagdas.productsapp.data.model.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : ProductsAppFragment<ProductsFragmentBinding, ProductsViewModel>(),
    ProductItemEventHandler {
    override val viewModel: ProductsViewModel by viewModels()

    private val progressDialog by lazy {
        WidgetProgressDialog(requireContext()).also {
            lifecycle.addObserver(it)
        }
    }

    private lateinit var productsAdapter: ProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onBindView(binding: ProductsFragmentBinding) {
        initRecyclerView()
        initAdapter()
    }

    override fun observeViewModel() {
        viewModel.run {
            observeLiveData(showLoadingLiveData) {
                progressDialog.showOrHide(it)
            }
            observeFlow(products) {
                productsAdapter.submitList(it) {
                    viewModel.showLoading.value = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        val productsLayoutManager =
            GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        binding.products.layoutManager = productsLayoutManager
    }

    private fun initAdapter() {
        productsAdapter = ProductListAdapter(this)
        binding.products.setAdapter(productsAdapter, this)
    }

    override fun onProductClick(product: Product) {
        product.id?.let {
            // TODO navigate detail
        }
    }

    override fun provideToolbar() = WidgetToolbar(
        title = getString(R.string.products_title),
    )

    override fun provideBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> ProductsFragmentBinding {
        return ProductsFragmentBinding::inflate
    }
}
