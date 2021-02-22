package com.oratakashi.jetpackacademy.ui.tv

import android.widget.EditText
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TvViewModel @ViewModelInject constructor(
    val repository : Repository
): ViewModel() {
    val state : MutableLiveData<TvState> by lazy {
        MutableLiveData<TvState>()
    }

    val data : MutableLiveData<PagedList<DataTv>> by lazy {
        MutableLiveData<PagedList<DataTv>>()
    }

    fun getTv(){
        repository.getTv(state, data)
    }

    fun setupSearch(editText: EditText){
        editText.textChangeEvents()
            .skipInitialValue()
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<TextViewTextChangeEvent>() {
                override fun onNext(t: TextViewTextChangeEvent) {
                    val keyword = t.text.toString()
                    if(keyword.trim{it <= ' '}.isNotEmpty() && keyword.trim{it <= ' '}.length >= 3) {
                        repository.searchTvLimit(keyword, state, data)
                    }else{
                        repository.getTv(state, data)
                    }
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
            .let { return@let repository.getDisposible()::add }
    }
}