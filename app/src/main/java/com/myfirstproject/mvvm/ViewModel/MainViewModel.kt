package com.myfirstproject.mvvm.ViewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import android.app.Dialog
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import com.myfirstproject.mvvm.R

class MainViewModel : ViewModel() {
    private var _count1  = MutableLiveData<Int>().apply {value = 0}
    private val _count2  = MutableLiveData<Int>().apply {value = 0}

    var showDialog : String = ""

    val count1 : LiveData<Int> get() = _count1
    val count2 : LiveData<Int> get() = _count2

    fun increment1() {
        _count1.value = (_count1.value ?: 0) + 1
    }
    fun increment2() {
        _count2.value = (_count2.value ?: 0) + 1
    }

    fun setValue(str: String) {
        showDialog = str
    }

    fun resets() {
        _count1.value = 0
        _count2.value = 0
        showDialog = ""
    }

}