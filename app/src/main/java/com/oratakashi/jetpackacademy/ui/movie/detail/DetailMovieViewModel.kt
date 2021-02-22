package com.oratakashi.jetpackacademy.ui.movie.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oratakashi.jetpackacademy.data.model.movie.DataMovie
import com.oratakashi.jetpackacademy.data.repository.Repository
import kotlinx.coroutines.*

class DetailMovieViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    val state : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun add(data : DataMovie){
        CoroutineScope(Dispatchers.IO).launch {
            val checkData = async { repository.checkData(data) }
            if(checkData.await().isEmpty()){
                repository.addData(data)
                withContext(Dispatchers.Main){
                    state.postValue(true)
                }
            }else{
                repository.deleteData(data)
                withContext(Dispatchers.Main){
                    state.postValue(false)
                }
            }
        }
    }

    fun check(data : DataMovie){
        CoroutineScope(Dispatchers.IO).launch {
            val checkData = async { repository.checkData(data) }
            if(checkData.await().isNotEmpty()){
                withContext(Dispatchers.Main){
                    state.postValue(true)
                }
            }else{
                withContext(Dispatchers.Main){
                    state.postValue(false)
                }
            }
        }
    }
}