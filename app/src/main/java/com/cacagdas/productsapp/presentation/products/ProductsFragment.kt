package com.cacagdas.productsapp.presentation.products

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.cacagdas.productsApp.R
import com.cacagdas.productsApp.databinding.ProductsFragmentBinding
import com.cacagdas.productsapp.core.base.ProductsAppFragment
import com.cacagdas.productsapp.core.util.extension.observeFlow
import com.cacagdas.productsapp.core.util.extension.observeLiveData
import com.cacagdas.productsapp.core.util.extension.visibleOrGone
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

    override fun onBindView(binding: ProductsFragmentBinding) {
        initRecyclerView()
        initAdapter()
    }

    override fun observeViewModel() {
        viewModel.run {
            observeLiveData(showLoadingLiveData) {
                progressDialog.showOrHide(it)
            }
            observeLiveData(showErrorMessageLiveData) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
            observeLiveData(emptyViewVisibilityLiveData) {
                binding.run {
                    products.visibleOrGone(!it)
                    emptyView.visibleOrGone(it)
                    viewModel.showLoading.value = false
                }
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
        findNavController().navigate(
            ProductsFragmentDirections.actionProductsFragmentToDetailFragment(
                product.id,
                product.name
            )
        )
    }

    override fun provideToolbar() = WidgetToolbar(
        title = getString(R.string.products_title),
    )

    override fun provideBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> ProductsFragmentBinding {
        return ProductsFragmentBinding::inflate
    }
}
