package com.cacagdas.productsapp.presentation.products

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.cacagdas.productsApp.databinding.ProductItemBinding
import com.cacagdas.productsapp.core.util.extension.loadImage
import com.cacagdas.productsapp.data.model.Product

class ProductViewHolder(
    private val eventHandler: ProductItemEventHandler,
    private val binding: ProductItemBinding,
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private var product: Product? = null

    init {
        binding.root.apply {
            setOnClickListener(this@ProductViewHolder)
        }
    }

    fun bind(item: Product) {
        product = item
        binding.apply {
            image.loadImage(item.image)
            name.text = item.name
            price.text = item.price?.toString()
        }
    }

    override fun onClick(v: View?) {
        val itemPosition = bindingAdapterPosition
        if (itemPosition != RecyclerView.NO_POSITION) {
            product?.let { eventHandler.onProductClick(it) }
        }
    }
}