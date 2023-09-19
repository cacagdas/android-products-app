package com.cacagdas.productsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.cacagdas.productsapp.core.util.Result
import com.cacagdas.productsapp.data.model.Product
import com.cacagdas.productsapp.domain.GetLocalProductDetail
import com.cacagdas.productsapp.domain.GetProductDetail
import com.cacagdas.productsapp.presentation.detail.DetailFragment
import com.cacagdas.productsapp.presentation.detail.DetailViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @RelaxedMockK
    private lateinit var getProductDetail: GetProductDetail

    @RelaxedMockK
    private lateinit var getLocalProductDetail: GetLocalProductDetail

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        MockKAnnotations.init(this, relaxUnitFun = true)
        val savedStateHandle = SavedStateHandle().apply {
            set(DetailFragment.ARG_PRODUCT_ID, "1")
            set(DetailFragment.ARG_PRODUCT_TITLE, "title")
        }
        viewModel = DetailViewModel(savedStateHandle, getProductDetail, getLocalProductDetail)
    }

    @Test
    fun `when get product returns error verify get local product called`() {
        val productId = "1"
        coEvery { getProductDetail.invoke(GetProductDetail.Params(productId)) } returns Result.Error(
            NullPointerException()
        )
        viewModel.getProduct()
        verify { viewModel.getProductLocal(productId) }
    }

    @Test
    fun `check if get product returns product with given id`() {
        val productId = "1"
        coEvery { getProductDetail.invoke(GetProductDetail.Params(productId)) } returns Result.Success(
            Product(productId, null, null, null, null)
        )
        viewModel.getProduct()
        assertTrue(productId == viewModel.product.value?.id)
    }
}
