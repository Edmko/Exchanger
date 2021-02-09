package test.privat.exchanger.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer

abstract class BaseFragment<VIEW_MODEL : BaseViewModel, BINDING : ViewBinding>(@LayoutRes layout: Int) :
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }
    override fun onStart() {
        super.onStart()
        bindViewModel()
    }

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }

    private fun Disposable.autoDispose() = disposables.add(this)

    infix fun <T> BaseViewModel.Data<T>.bind(consumer: (T) -> Unit) {
        observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer)
            .autoDispose()
    }

    infix fun <T> BaseViewModel.Data<T>.bind(consumer: Consumer<in T>) {
        observable.observeOn(AndroidSchedulers.mainThread())
            .subscribe(consumer)
            .autoDispose()
    }
}