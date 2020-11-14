package com.oratakashi.jetpackacademy.ui.movie

import android.widget.EditText
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jakewharton.rxbinding3.widget.TextViewTextChangeEvent
import com.jakewharton.rxbinding3.widget.textChangeEvents
import com.oratakashi.jetpackacademy.data.model.movie.ResponseMovie
import com.oratakashi.jetpackacademy.data.repository.Repository
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MovieViewModel @ViewModelInject constructor(
    val repository : Repository
): ViewModel() {
    val state : MutableLiveData<MovieState> by lazy {
        MutableLiveData<MovieState>()
    }

    fun getMovie() {
         repository.getMovie(state)
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
                        repository.searchMovie(keyword, state)
                    }else{
                        repository.getMovie(state)
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