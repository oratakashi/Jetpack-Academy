package com.oratakashi.jetpackacademy.ui.tv.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oratakashi.jetpackacademy.data.model.tv.DataTv
import com.oratakashi.jetpackacademy.data.repository.Repository
import kotlinx.coroutines.*

class DetailTvViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {
    val state : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun add(data : DataTv){
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

    fun check(data : DataTv){
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