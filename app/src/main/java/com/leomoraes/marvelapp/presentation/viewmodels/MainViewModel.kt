package com.leomoraes.marvelapp.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leomoraes.marvelapp.data.model.Hero
import com.leomoraes.marvelapp.data.model.ViewState
import com.leomoraes.marvelapp.data.model.postError
import com.leomoraes.marvelapp.data.model.postSuccess
import com.leomoraes.marvelapp.data.repository.CharRepository
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val repository: CharRepository,
    private val postExecutionScheduler: Scheduler
) : ViewModel() {
    private lateinit var taskDisposable: Disposable
    private val pageSize: Int = 50
    private var page: Int = 0
    private var total: Int = 0

    private val _heroLiveData = MutableLiveData<ViewState<List<Hero>>>()
    val heroLiveData: LiveData<ViewState<List<Hero>>>
        get() = _heroLiveData

    init {
        loadData()
    }

    private fun loadData() {
        _heroLiveData.postValue(ViewState(ViewState.Status.LOADING))

        taskDisposable = repository.getAllChars(page, pageSize)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionScheduler)
            .subscribe(
                {
                    if (it.code == 200) {
                        total = it.data.total
                        _heroLiveData.postSuccess(it.data.results)
                    } else {
                        _heroLiveData.postError(Exception(""))
                    }
                },
                {
                    _heroLiveData.postError(it)
                }
            )
    }

    fun nextPage() {
        page++
        loadData()
    }

    fun previousPage() {
        page--
        loadData()
    }

    fun hasPrevious(): Boolean = page != 0

    fun hasNext(): Boolean = page * pageSize < total - pageSize

    override fun onCleared() {
        taskDisposable.dispose()
        super.onCleared()
    }
}