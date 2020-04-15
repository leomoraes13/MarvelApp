package com.leomoraes.marvelapp.data.model

import androidx.lifecycle.MutableLiveData

class ViewState<D>(
    private val status: Status,
    val data: D? = null,
    val error: Throwable? = null
) {

    fun handleIt(
        success: (D) -> Unit,
        error: (String) -> Unit,
        loading: () -> Unit = {}
    ) {

        when (this.status) {
            Status.SUCCESS -> {
                this.data?.let {
                    success(it)
                }
            }
            Status.ERROR -> {
                this.error?.message?.let {
                    error(it)
                }
            }
            Status.LOADING -> {
                loading()
            }
        }
    }

    enum class Status {
        LOADING, SUCCESS, ERROR
    }
}

fun <D> MutableLiveData<ViewState<D>>.postSuccess(data: D) {
    this.postValue(ViewState(ViewState.Status.SUCCESS, data = data))
}

fun <D> MutableLiveData<ViewState<D>>.postError(error: Throwable) {
    this.postValue(ViewState(ViewState.Status.ERROR, error = error))
}

fun <T> MutableLiveData<ViewState<T>>.postLoading() {
    this.postValue(ViewState(ViewState.Status.LOADING, null))
}

