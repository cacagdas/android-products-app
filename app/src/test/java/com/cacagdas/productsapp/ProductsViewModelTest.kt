package com.cacagdas.productsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cacagdas.productsapp.core.util.Result
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.data.model.ProductsResponse
import com.cacagdas.productsapp.domain.GetLocalProducts
import com.cacagdas.productsapp.domain.GetProducts
import com.cacagdas.productsapp.domain.StoreProducts
import com.cacagdas.productsapp.presentation.products.ProductsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class ProductsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getProducts: GetProducts

    @RelaxedMockK
    private lateinit var storeProducts: StoreProducts

    @RelaxedMockK
    private lateinit var getLocalProducts: GetLocalProducts

    private lateinit var viewModel: ProductsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this, relaxUnitFun = true)
        viewModel = ProductsViewModel(getProducts, storeProducts, getLocalProducts)
    }

    @Test
    fun `when get products returns error verify get local products called`() {
        coEvery { getProducts.invoke(GetProducts.Params(Unit)) } returns Result.Error(
            NullPointerException()
        )
        viewModel.getProductList()
        verify { viewModel.getLocalProductList() }
    }

    @Test
    fun `when get products returns error verify store product list never called`() {
        coEvery { getProducts.invoke(GetProducts.Params(Unit)) } returns Result.Error(
            NullPointerException()
        )
        viewModel.getProductList()
        val productList = mockk<List<Product>>()
        verify(exactly = 0) { viewModel.storeProductList(productList) }
    }

    @Test
    fun `when get products returns success verify store product list called`() {
        val productList = mockk<List<Product>>()
        coEvery { getProducts.invoke(GetProducts.Params(Unit)) } returns Result.Success(
            ProductsResponse(productList)
        )
        viewModel.getProductList()
        verify { viewModel.storeProductList(productList) }
    }
}
