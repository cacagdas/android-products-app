package com.cacagdas.productsapp.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.cacagdas.productsapp.MainActivity
import com.cacagdas.productsapp.core.widget.WidgetToolbar

abstract class ProductsAppFragment<Binding : ViewBinding, VM : ProductsAppViewModel> : Fragment() {

    abstract val viewModel: VM

    protected open val binding get() = _binding!!
    private var _binding: Binding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingInflater = provideBindingInflater()
        val viewBinding = bindingInflater(inflater, container, false)
        _binding = viewBinding
        onBindView(viewBinding)
        observeViewModel()
        return viewBinding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).updateToolbar(provideToolbar())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    abstract fun onBindView(binding: Binding)

    abstract fun observeViewModel()

    abstract fun provideToolbar(): WidgetToolbar

    abstract fun provideBindingInflater(): (LayoutInflater, ViewGroup?, Boolean) -> Binding
}
