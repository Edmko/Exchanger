package test.privat.exchanger.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VIEW_MODEL : ViewModel, BINDING : ViewBinding>(@LayoutRes layout: Int) :
    Fragment(layout) {

    private val disposables = CompositeDisposable()

    abstract val binding: BINDING

    abstract val viewModel: VIEW_MODEL

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    abstract fun setupView()
    abstract fun bindViewModel()

    override fun onStart() {
        super.onStart()
        bindViewModel()
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}