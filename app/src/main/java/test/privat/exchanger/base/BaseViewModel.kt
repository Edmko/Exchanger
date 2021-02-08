package test.privat.exchanger.base

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import com.jakewharton.rxrelay3.BehaviorRelay
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableSource
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Consumer
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import test.privat.exchanger.domain.interactor.UseCase
import timber.log.Timber
import java.util.concurrent.atomic.AtomicReference

abstract class BaseViewModel : ViewModel() {

    enum class State {
        INITIAL, LOADING, SUCCESS, FAILURE
    }

    protected val disposable = CompositeDisposable()

    protected fun <T> asyncSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    protected fun Disposable.autoDispose() {
        addTo(disposable)
    }

    protected fun <T> Data<T>.consume(value: T) {
        consumer.accept(value)
    }

    protected val <T> Data<T>.consumer: Consumer<T> get() = relay

    /**
     * Wrapper for data which emmit last value after subscribe. Also holds data from use cases
     * @see UseCase
     *
     *  When need change data - use
     *  @property observable
     *
     *  If need emmit last value use
     *  @property refresh
     *
     */
    @SuppressLint("CheckResult")
    inner class Data<T>(private val defaultValue: T? = null) {
        internal val relay = if (defaultValue != null) {
            BehaviorRelay.createDefault(defaultValue).toSerialized()
        } else {
            BehaviorRelay.create<T>().toSerialized()
        }

        private val lastValue = if (defaultValue != null) {
            AtomicReference<T?>(defaultValue)
        } else {
            AtomicReference()
        }

        val observable: Observable<T>
            get() = relay.doOnSubscribe {
                lastValue.get()?.let {
                    if (defaultValue == null) {
                        consumer.accept(it)
                    }
                }
            }

        val value: T get() = lastValue.get() ?: throw UninitializedPropertyAccessException()

        val hasValue: Boolean
            get() = lastValue.get() != null

        fun getSafe(fallbackValue: T): T {
            return if (hasValue) {
                value
            } else {
                fallbackValue
            }
        }

        fun refresh() {
            if (hasValue) {
                consume(value)
            }
        }

        init {
            relay.subscribe {
                lastValue.set(it)
            }
        }
    }

    protected val <T> Trigger<T>.observable: Observable<T> get() = relay

    /**
     * Wrapper for trigger actions which not replay when configuration was changed
     * Use it for trigger or subscribe for actions from Fragment and send it to viewModel observer
     */
    inner class Trigger<T> {
        internal val relay = PublishRelay.create<T>().toSerialized()

        val consumer: Consumer<T> get() = relay

        fun triggerManual(item: T) {
            consumer.accept(item)
        }
    }

    protected val <T> Call<T>.consumer: Consumer<T> get() = relay

    /**
     * Wrapper for trigger actions which not replay when configuration was changed
     *
     * Use it for get data from ViewModel and send it to consumer into Fragment
     */
    inner class Call<T> {
        internal val relay = PublishRelay.create<T>().toSerialized()

        val observable: Observable<T> = relay
            .publish()
            .apply { connect() }

        fun compose(composer: (upstream: Observable<T>) -> ObservableSource<T>): Call<T> {
            observable.compose(composer)
            return this
        }

        fun filter(predicate: (T) -> Boolean): Call<T> {
            return compose {
                val obs = Observable.wrap(it)
                obs.filter(predicate)
            }
        }
    }

    protected fun <T : Any, Params> UseCase<Params, T>.subscribe(
        consumer: Consumer<UseCase.Status<T>>? = null
    ): Disposable {
        return if (consumer != null) {
            subscribe(asyncSingle(), consumer)
        } else {
            subscribe(asyncSingle(), Consumer { })
        }
    }


    private fun <T> UseCase.Status<T>.explodeTo(
        data: Data<T>,
        state: Data<State>
    ) {
        when (this) {
            is UseCase.Status.Loading -> state.consume(State.LOADING)
            is UseCase.Status.Success -> {
                state.consume(State.SUCCESS)
                data.consume(this.data)
            }
            is UseCase.Status.Failure -> {
                state.consume(State.FAILURE)
            }
        }
    }

    protected fun <T> explodeTo(result: UseCaseResult<T>): Consumer<UseCase.Status<T>> {
        return explodeTo(result.data, result.error, result.state)
    }

    private fun <T> createData(defaultValue: T? = null): Data<T> = Data(defaultValue)

    private fun <T> createTrigger(): Trigger<T> = Trigger()

    private fun <T> createCall(): Call<T> = Call()

    /**
     * Class holds Relays which get data from UseCase
     */
    inner class UseCaseResult<T> {
        fun consume(success: UseCase.Status.Success<T>) {
            success.explodeTo(data, state)
        }

        val data: Data<T> = createData()
        val error: Call<String> = createCall()
        val state: Data<State> = createData(State.INITIAL)
    }

    private fun <T> explodeTo(
        data: Data<T>,
        error: Call<String>,
        state: Data<State>
    ): Consumer<UseCase.Status<T>> {
        return Consumer {
            it.explodeTo(data, state)
            if (it is UseCase.Status.Failure) {
                //TODO set error handler
                Timber.e(it.e)
            }
        }
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

}