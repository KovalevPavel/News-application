package com.github.newsapp.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import moxy.MvpAppCompatFragment


abstract class FragmentViewBinding<T : ViewBinding>(
    private val inflateBinding: (
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean
    ) -> T
) : MvpAppCompatFragment() {
    private var _binder: T? = null
    protected val binder: T
        get() = _binder!!

    final override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binder = inflateBinding.invoke(inflater, container, false)
        return binder.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binder = null
    }
}