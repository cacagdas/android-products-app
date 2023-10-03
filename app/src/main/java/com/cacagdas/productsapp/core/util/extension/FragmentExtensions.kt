package com.cacagdas.productsapp.core.util.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.cacagdas.productsapp.core.widget.WidgetRecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Fragment.releaseAdapterOnDestroy(recyclerView: WidgetRecyclerView) {
    viewLifecycleOwner.lifecycle.addObserver(recyclerView)
}

inline fun <T> Fragment.observeFlow(
    flow: Flow<T>?,
    crossinline onChanged: suspend (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow?.collectLatest {
                onChanged.invoke(it)
            }
        }
    }
}

fun <T> Fragment.observeLiveData(
    liveData: LiveData<T>,
    onChanged: (T) -> Unit
) {
    liveData.observe(viewLifecycleOwner, onChanged)
}
