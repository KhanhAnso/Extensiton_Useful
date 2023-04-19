package com.example.useful_extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    val fruit: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            this.value = "Orange"
        }
    }

    fun fruitOnValueChanged() = fruit as LiveData<String>
}